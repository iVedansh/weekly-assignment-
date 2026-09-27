import java.util.*;

interface ScoringRule {
    double calculate(double idea, double execution, double presentation);
    String getName();
}
class InnovationScoringRule implements ScoringRule {
    public double calculate(double idea,double execution,double presentation){return idea*.50+execution*.30+presentation*.20;}
    public String getName(){return "Innovation";}
}
class OpenScoringRule implements ScoringRule {
    public double calculate(double idea,double execution,double presentation){return (idea+execution+presentation)/3.0;}
    public String getName(){return "Open";}
}
class Student {
    private final String name;
    public Student(String name){this.name=name;}
    public String getName(){return name;}
}
class Score {
    private final double idea,execution,presentation;
    public Score(double idea,double execution,double presentation){validate(idea);validate(execution);validate(presentation);this.idea=idea;this.execution=execution;this.presentation=presentation;}
    private void validate(double v){if(v<0||v>10)throw new IllegalArgumentException("Rating must be between 0 and 10");}
    public double finalScore(ScoringRule rule){return rule.calculate(idea,execution,presentation);}
}
class Project {
    private final String name; private final Team team; private Score score;
    public Project(String name,Team team){this.name=name;this.team=team;}
    public String getName(){return name;}
    public void recordScore(Score score,Hackathon h){if(h.getState()==HackathonState.PUBLISHED)throw new IllegalStateException("Results have already been published");this.score=score;}
    public double getFinalScore(ScoringRule rule){if(score==null)throw new IllegalStateException("No score recorded");return score.finalScore(rule);}
}
class Team {
    private final String name; private final List<Student> members; private final ScoringRule track; private Project project;
    public Team(String name,List<Student> members,ScoringRule track){if(members.size()<2||members.size()>4)throw new IllegalArgumentException("A team must have 2 to 4 members");this.name=name;this.members=new ArrayList<>(members);this.track=track;}
    public String getName(){return name;} public List<Student> getMembers(){return Collections.unmodifiableList(members);} public ScoringRule getTrack(){return track;}
    public void submitProject(String projectName){if(project!=null)throw new IllegalStateException("A team can submit only one project");project=new Project(projectName,this);}
    public Project getProject(){return project;}
}
enum HackathonState{OPEN,JUDGING,PUBLISHED}
class Hackathon {
    private final String name; private final List<Team> teams=new ArrayList<>(); private final Set<Student> registeredStudents=new HashSet<>(); private HackathonState state=HackathonState.OPEN;
    public Hackathon(String name){this.name=name;}
    public void registerTeam(Team team){if(state!=HackathonState.OPEN)throw new IllegalStateException("Registration is closed");for(Student s:team.getMembers())if(registeredStudents.contains(s))throw new IllegalArgumentException("A student can belong to only one team per hackathon");teams.add(team);registeredStudents.addAll(team.getMembers());System.out.println("Team "+team.getName()+" registered ("+team.getMembers().size()+" members, "+team.getTrack().getName()+" track).");}
    public void startJudging(){state=HackathonState.JUDGING;} public void publishResults(){state=HackathonState.PUBLISHED;System.out.println("Results published.");} public HackathonState getState(){return state;}
}
class Judge {
    public void score(Project project,double idea,double execution,double presentation,Hackathon h){if(h.getState()==HackathonState.PUBLISHED)throw new IllegalStateException("Results have already been published");project.recordScore(new Score(idea,execution,presentation),h);System.out.println("Score recorded for '"+project.getName()+"'.");}
}
public class Problem1_CodeSprintJudgingDesk {
    public static void main(String[] args){
        Hackathon h=new Hackathon("Code Sprint");
        Team byteBusters=new Team("ByteBusters",Arrays.asList(new Student("Asha"),new Student("Ravi"),new Student("Neha")),new InnovationScoringRule());
        h.registerTeam(byteBusters);
        try{h.registerTeam(new Team("SoloCoder",List.of(new Student("Kiran")),new OpenScoringRule()));}catch(Exception e){System.out.println("Registration failed: "+e.getMessage()+".");}
        byteBusters.submitProject("SmartAttend");System.out.println("Project 'SmartAttend' submitted by ByteBusters.");h.startJudging();
        Judge judge=new Judge();judge.score(byteBusters.getProject(),8,7,9,h);System.out.printf("Final score: %.2f%n",byteBusters.getProject().getFinalScore(byteBusters.getTrack()));h.publishResults();
        try{judge.score(byteBusters.getProject(),10,7,9,h);}catch(Exception e){System.out.println("Rescore rejected: "+e.getMessage()+".");}
    }
}