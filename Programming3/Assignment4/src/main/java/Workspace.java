import java.util.List;

public class Workspace {
    private String workspaceName;
    private String workspaceDescription;
    private UserAccount owner;
    private List<UserAccount> collaborators;

    public Workspace(String workspaceName, String workspaceDescription, UserAccount owner) {
        this.workspaceName = workspaceName;
        this.workspaceDescription = workspaceDescription;
        this.owner = owner;
    }

    public void addCollaborator(UserAccount user) {
        collaborators.add(user);
    }

    @Override
    public String toString() {
        return "";
    }
}
