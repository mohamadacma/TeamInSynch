import TeamInSynchClient from '../api/teamInSynchClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
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
        this.bindClassMethods(['mount', 'searchMembers', 'displaySearchResults', 'getHTMLForSearchResults'], this);
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        this.dataStore.addChangeListener(this.displaySearchResults);
        console.log("searchMembers constructor");
    }

    /**
     * Add the header to the page and load the TeamInSynchClient.
     */
    mount() {
        // Wire up the form's 'submit' event and the button's 'click' event to the search method.
        document.getElementById('search-members-form').addEventListener('submit', this.searchMembers);
        document.getElementById('search-btn').addEventListener('click', this.searchMembers);
        this.header.addHeaderToPage();
        this.client = new TeamInSynchClient();
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

       let html = '<table><tr><th>Name</th><th>City</th><th>Team</th></tr>';
        for (const res of searchResults) {
            html += `
            <tr>
                <td>
                    <a href="member.html?id=${res.id}">${res.memberName}</a>
                </td>
                <td>${res.city}</td>
                <td>${res.teamName}</td>
            </tr>`;
        }
        html += '</table>';

        return html;
    }

}

    /**
    * Main method to run when the page contents have loaded.
    */
    const main = async () => {
    const searchMembers = new SearchMembers();
    searchMembers.mount();
    };

    window.addEventListener('DOMContentLoaded', main);
