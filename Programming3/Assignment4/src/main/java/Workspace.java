import java.util.*;

public class Workspace {
    private String workspaceName;
    private String workspaceDescription;
    private UserAccount owner;
    private List<UserAccount> collaborators;

    public Workspace(String workspaceName, String workspaceDescription, UserAccount owner) {
        this.workspaceName = workspaceName;
        this.workspaceDescription = workspaceDescription;
        this.owner = owner;
        collaborators = new ArrayList<UserAccount>();
        }

    public void addCollaborator(UserAccount user) {

        collaborators.add(user);
    }

    public List<UserAccount> getCollaborators() {
        return collaborators;
    }

    @Override
    public String toString() {
        String collaboratorsString = "";
        for(UserAccount c : collaborators) {
            collaboratorsString += c.getName();
            collaboratorsString += ", ";
        }
        return String.format("\nWorkspace Name: %s, Collaborators: %s", workspaceName, collaboratorsString);
    }
}
