import TeamInSynchClient from '../api/teamInSynchClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import Authenticator from '../api/authenticator';
/**
 * Logic needed for the search Members page of the website.
 */

const SEARCH_CRITERIA_KEY = 'search-criteria';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
   [SEARCH_CRITERIA_KEY]: {
           teamName: '',
           city: '',
           memberName: ''
       },
    [SEARCH_RESULTS_KEY]: [],
};

/**
 * Logic needed for the search Members page of the website.
 */
class SearchMembers extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'searchMembers', 'displaySearchResults', 'getHTMLForSearchResults', 'handleLogin', 'handleLogout'], this);
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        this.authenticator = new Authenticator();
        this.dataStore.addChangeListener(this.displaySearchResults);
        console.log("searchMembers constructor");
    }

    /**
     * Add the header to the page and load the TeamInSynchClient.
     */
    async mount() {
        // Wire up the form's 'submit' event and the button's 'click' event to the search method.
        document.getElementById('search-members-form').addEventListener('submit', this.searchMembers);
        document.getElementById('search-btn').addEventListener('click', this.searchMembers);



        this.header.addHeaderToPage();
        this.client = new TeamInSynchClient();
        const currUser = await this.client.getIdentity();
        console.log( "user"+ currUser);
                if(!currUser){
                    this.client.login();
                }
    }

        /**
         * Handle the login button click.
         */
         async handleLogin(evt) {
                 evt.preventDefault();
                 await this.authenticator.login();
                 window.location.reload();
             }

             /**
                  * Handle the logout button click.
                  */
                 async handleLogout(evt) {
                     evt.preventDefault();
                     await this.authenticator.logout();
                     window.location.reload();
                 }

    /**
     * Uses the client to perform the search,
     * then updates the datastore with the criteria and results.
     * @param evt The "event" object representing the user-initiated event that triggered this method.
     */
    async searchMembers(evt) {
        // Prevent submitting the from from reloading the page.
        evt.preventDefault();

         const searchCriteria = document.getElementById('search-criteria').value;
         const previousSearchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);

        // If the user didn't change the search criteria, do nothing
        if (previousSearchCriteria === searchCriteria) {
                    return;
               }
        if (searchCriteria) {
                    const [teamName, city, memberName] = searchCriteria.split(',');
                    const results = await this.client.searchMembers(teamName?.trim(), city?.trim(), memberName?.trim());

            this.dataStore.setState({
                   [SEARCH_CRITERIA_KEY]: searchCriteria,
                   [SEARCH_RESULTS_KEY]: results,
            });
        } else {
            this.dataStore.setState(EMPTY_DATASTORE_STATE);
        }
    }


    /**
     * Pulls search results from the datastore and displays them on the html page.
     */
    displaySearchResults() {
        const searchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);
        const searchResults = this.dataStore.get(SEARCH_RESULTS_KEY);

        const searchResultsContainer = document.getElementById('search-results-container');
        const searchCriteriaDisplay = document.getElementById('search-criteria-display');
        const searchResultsDisplay = document.getElementById('search-results-display');

       if (searchCriteria === '') {
           searchResultsContainer.classList.add('hidden');
           searchCriteriaDisplay.innerHTML = '';
           searchResultsDisplay.innerHTML = '';
               } else {
           searchResultsContainer.classList.remove('hidden');
           searchCriteriaDisplay.innerHTML = `"${searchCriteria}"`;
            searchResultsDisplay.innerHTML = this.getHTMLForSearchResults(searchResults);
               }
    }

    /**
     * Create appropriate HTML for displaying searchResults on the page.
     * @param searchResults An array of members objects to be displayed on the page.
     * @returns A string of HTML suitable for being dropped on the page.
     */
    getHTMLForSearchResults(searchResults) {
        if (searchResults.length === 0) {
            return '<h4>No results found</h4>';
        }

       let html = `
               <table>
                   <thead>
                       <tr>
                           <th>Name</th>
                           <th>City</th>
                           <th>Team</th>
                           <th>Phone</th>
                           <th>Background</th>
                           <th>Role</th>
                           <th>Email</th>
                           <th>ID</th>
                           <th>Actions</th>
                       </tr>
                   </thead>
                   <tbody>`;

           for (const res of searchResults) {
               html += `
               <tr>
                   <td><a href="member.html?id=${res.id}">${res.memberName}</a></td>
                   <td>${res.city}</td>
                   <td>${res.teamName}</td>
                   <td>${res.phoneNumber}</td>
                   <td>${res.background}</td>
                   <td>${res.role}</td>
                   <td>${res.memberEmail}</td>
                   <td>${res.memberId}</td>
                   <td>
                   <select onchange="handleAction(this.value, '${res.memberId}')">
                   <option value="">Select Action</option>
                   <option value="edit">Edit</option>
                   <option value="delete">Delete</option>
                   <option value="show weather">Weather</option>
                   <option value="show news"> News</option>
                  </select>
                 </td>
               </tr>`;
           }
           html += '</tbody></table>';

        return html;
    }
}
    /**
         * Handle the action from the dropdown menu.
         * @param {String} action The action to perform (edit or delete).
         * @param {String} memberId The ID of the member to act on.
         */
        window.handleAction = async (action, memberId) => {
            const client = new TeamInSynchClient();
            if (action === 'edit') {
                window.location.href = `updateMember.html?id=${memberId}`;
            } else if (action === 'delete') {
                if (confirm('Are you sure you want to delete this member?')) {
                   try {
                        const response = await client.deleteMember(memberId);
                        alert(response.message);
                        window.location.reload();
                        } catch (error) {
                            alert('Failed to delete member: ${error.message}');
                        }
                }
            } else if (action === 'show weather') {
                     window.location.href = `weather.html?id=${memberId}`;
            } else if (action === 'show news') {
                              window.location.href = `news.html?id=${memberId}`;
                          }
        };

        /**
         * Main method to run when the page contents have loaded.
         */
        function main() {
            const searchMembers = new SearchMembers();
            searchMembers.mount();
        }

        window.addEventListener('DOMContentLoaded', main);
