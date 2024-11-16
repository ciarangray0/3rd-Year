import java.util.*;

/**
 * Represents a workspace with a name, description, an owner, and a list of collaborators.
 */
public class Workspace {
    private String workspaceName;
    private String workspaceDescription;
    private UserAccount owner;
    private List<UserAccount> collaborators;

    /**
     * Constructs a new Workspace with the specified name, description, and owner.
     *
     * @param workspaceName        The name of the workspace.
     * @param workspaceDescription description of the workspace.
     * @param owner                The owner of the workspace.
     */
    public Workspace(String workspaceName, String workspaceDescription, UserAccount owner) {
        this.workspaceName = workspaceName;
        this.workspaceDescription = workspaceDescription;
        this.owner = owner;
        collaborators = new ArrayList<UserAccount>(); //instansiate collabortasors array
    }

    /**
     * Adds a user to the collaborators array
     *
     * @param user The UserAccount to add as a collaborator.
     */
    public void addCollaborator(UserAccount user) {
        collaborators.add(user);
    }

    /**
     * Returns the list of collaborators in this workspace.
     *
     * @return a list of UserAccount objects representing the collaborators.
     */
    public List<UserAccount> getCollaborators() {
        return collaborators;
    }

    /**
     * Returns a string representation of this workspace withthe workspace name and a comma-separated list of collaborators names.
     *
     *
     * @return a string representation of the workspace.
     */
    @Override
    public String toString() {
        String collaboratorsString = "";
        for (UserAccount c : collaborators) {
            collaboratorsString += c.getName() + ", ";
        }
        //get rid of whitespace and comma at the end of the array
        if(collaboratorsString.endsWith(", ")) {
            collaboratorsString = collaboratorsString.substring(0, collaboratorsString.length() - 2);
        }
        return String.format("\nWorkspace Name: %s, Collaborators: %s", workspaceName, collaboratorsString);
    }
}

