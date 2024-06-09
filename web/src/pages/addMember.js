import TeamInSynchClient from '../api/teamInSynchClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the add Member page of the website.
 */
class AddMember extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToHomePage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToHomePage);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the TeamInSynchClient.
     */
    mount() {
        document.getElementById('save-btn').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new TeamInSynchClient();
    }

    /**
     * Method to run when the add member submit button is pressed. Call the TeamInSynchService to create the
     * member.
     */
    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const saveButton = document.getElementById('save-btn');
        const origButtonText = saveButton.innerText;
        saveButton.innerText = 'Loading...';

        const memberName = document.getElementById('member-name').value;
        const joinDate = document.getElementById('join-date').value;
        const phone = document.getElementById('member-phone').value;
        const city = document.getElementById('member-city').value;
        const background = document.getElementById('member-background').value;
        const role = document.getElementById('member-role').value;
        const email = document.getElementById('member-email').value;
        const teamName = document.getElementById('team-name').value;

        const member = await this.client.addMember(
        memberName,
        joinDate,
        phone,
        city,
        background,
        role,
        email,
        teamName,
        (error) => {
            saveButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('member', member);
    }

    /**
     * When the member is updated in the datastore, redirect to the home page.
     */
    redirectToHomePage() {
        const member = this.dataStore.get('member');
        if (member != null) {
            window.location.href = `/index.html`;
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const addMember = new AddMember();
    addMember.mount();
};

window.addEventListener('DOMContentLoaded', main);
