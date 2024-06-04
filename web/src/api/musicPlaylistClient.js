import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the MusicPlaylistService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class TeamInSynchClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'addMember', 'deleteMember', 'updateMember', 'searchMembers'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

   /**
        * Adds a new member to a team owned by a manager.
        * @param memberId Unique identifier for the member.
        * @param memberName Name of the member.
        * @param joinDate Date when the member joined.
        * @param phoneNumber Phone number of the member.
        * @param city City where the member is located.
        * @param background Background information about the member.
        * @param role Role of the member in the team.
        * @param memberEmail Email address of the member.
        * @param errorCallback (Optional) A function to execute if the call fails.
        * @returns The added member's metadata.
        */
   async addMember(memberId, memberName, joinDate, phoneNumber, city, background, role, memberEmail, errorCallback) {
       try {
            const token = await this.getTokenOrThrow("Only authenticated users can add a member.");
            const response = await this.axiosClient.post(`members`, {
                        memberId: memberId,
                        memberName: memberName,
                        joinDate: joinDate,
                        phoneNumber: phoneNumber,
                        city: city,
                        background: background,
                        role: role,
                        memberEmail: memberEmail,
                    }, {
                        headers: {
                            Authorization: `Bearer ${token}`
                             }
                           });
            return response.data.member;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Updates an existing member in a team owned by a manager.
     * @param memberId Unique identifier for the member.
     * @param memberName Name of the member.
     * @param joinDate Date when the member joined.
     * @param phoneNumber Phone number of the member.
     * @param city City where the member is located.
     * @param background Background information about the member.
     * @param role Role of the member in the team.
     * @param memberEmail Email address of the member.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The updated member's metadata.
     */
   async updateMember(memberId, memberName, joinDate, phoneNumber, city, background, role, memberEmail, errorCallback) {
       try {
             const token = await this.getTokenOrThrow("Only authenticated users can add a song to a playlist.");
             const response = await this.axiosClient.put(`members/${memberId}`, {
                              memberId: memberId,
                              memberName: memberName,
                              joinDate: joinDate,
                              phoneNumber: phoneNumber,
                              city: city,
                              background: background,
                              role: role,
                              memberEmail: memberEmail
                          }, {
                              headers: {
                                  Authorization: `Bearer ${token}`
                              }
                  });
                         return response.data.member;
                     } catch (error) {
                         this.handleError(error, errorCallback)
                     }
                 }
    /**
     * Deletes a member from a team owned by a manager.
     * @param memberId Unique identifier for the member.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns A success message or the error response.
     */
    async deleteMember(memberId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can delete a member.");
            const response = await this.axiosClient.delete(`members/${memberId}`, {
                           memberId: memberId,
            }, {
                headers: {
                      Authorization: `Bearer ${token}`
                             }
                         });
                         return response.data;
                     } catch (error) {
                         this.handleError(error, errorCallback)
                     }
            }
    /**
     * Search for a soong.
     * @param criteria A string containing search criteria to pass to the API.
     * @returns The playlists that match the search criteria.
     */
    async search(criteria, errorCallback) {
        try {
            const queryParams = new URLSearchParams({ q: criteria })
            const queryString = queryParams.toString();

            const response = await this.axiosClient.get(`playlists/search?${queryString}`);

            return response.data.playlists;
        } catch (error) {
            this.handleError(error, errorCallback)
        }

    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}
