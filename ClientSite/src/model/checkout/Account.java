package model.checkout;

import javax.xml.bind.annotation.*;
import model.exception.*;

/**
 * The Account object represents a logged customer
 * in the system. Holds information about the
 * user's account information, including id,
 * name and avatar.
 */
@XmlRootElement(name="customer")
public class Account {

    private String id;
    private String name;
    private String avatar; // user picture

    private AuthAuthority auth;
    private boolean signin = true;

    public Account() { } // To make JAXB happy

    /**
     * Constructs a new Account object
     * Only can/should be created by the
     * AuthAuthority. Package level access
     * only.
     *
     * @param auth      the authenication authority
     * @param id        the username (id)
     * @param name      the name of the user
     * @param avatar    URI to profile picture
     */
    Account(AuthAuthority auth, String id,
                String name, String avatar) {
        this.auth   = auth;
        this.id     = id;
        this.name   = name;
        this.avatar = avatar;
    }

    // Getters

    @XmlAttribute(name="account")
    public String getId() {
        if (!isValid()) {
            throw new UserAccessDeniedException();
        }
        return id;
    }

    public String getName() {
        if (!isValid()) {
            throw new UserAccessDeniedException();
        }
        return name;
    }

    public String getAvatar() {
        if (!isValid()) {
            throw new UserAccessDeniedException();
        }
        return avatar;
    }

    // Queries and Operations

    /**
     * Check if the user is still logged in.
     *
     * @return      true if the user is logged in,
     *              otherwise false.
     */
    public boolean isValid() {
        return signin;
    }

    /**
     * Revalidate the account via the
     * AuthAuthority. If the user is
     * logged out, he or she will be
     * relogged in, if revalidated.
     *
     * @return      true if user revalidation is
     *              successful, otherwise false.
     */
    public boolean revalidate() {
        signin = auth.checkCredentials(this);
        return signin;
    }

    /**
     * Signs/Logs the user out.
     */
    public void signOut() {
        signin = false;
    }

} // User
