import TeamInSynchClient from '../api/teamInSynchClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the update Member page of the website.
 */
class ViewPlaylist extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addMemberToPage', 'updateMember'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addMemberToPage);
        this.dataStore.addChangeListener(this.addSongsToPage);
        this.header = new Header(this.dataStore);
        console.log("updateMember constructor");
    }

    /**
     * Once the client is loaded, get the member data.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const memberId = urlParams.get('id');
        document.getElementById('member-name').value = "Loading...";
        const member = await this.client.getMember(memberId);
        this.dataStore.set('member', member);
    }

    /**
     * Add the header to the page and load the TeamInSynchClient.
     */
    mount() {
        document.getElementById('save-btn').addEventListener('click', this.updateMember);

        this.header.addHeaderToPage();

        this.client = new TeamInSynchClient();
        this.clientLoaded();
    }

    /**
     *When the member is updated in the datastore, update the member details on the page.
     */
    addMemberToPage() {
        const member = this.dataStore.get('member');
        if (member == null) {
            return;
        }

        document.getElementById('member-name').value = member.memberName;
        document.getElementById('member-city').value = member.city;
        document.getElementById('member-email').value = member.memberEmail;
        document.getElementById('member-phone').value = member.phoneNumber;
        document.getElementById('join-date').value = member.joinDate;
        document.getElementById('member-role').value = member.role;
        document.getElementById('member-background').value = member.background;
        }

    /**
     * Method to run when the update member submit button is pressed. Call the TeamInSynchService to update the
     * member.
     */
    async updateMember(event) {
        event.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const saveButton = document.getElementById('save-btn');
        const origButtonText = saveButton.innerText;
        saveButton.innerText = 'Loading...';

        const member = this.dataStore.get('member');
        if (member == null) {
            return;
        }

         const memberName = document.getElementById('member-name').value;
         const city = document.getElementById('member-city').value;
         const email = document.getElementById('member-email').value;
         const joinDate = document.getElementById('join-date').value;
         const phone = document.getElementById('member-phone').value;
         const role = document.getElementById('member-role').value;
         const background = document.getElementById('member-background').value;

        const updatedMember = await this.client.updateMember(member.memberId, {
                memberName,
                city,
                memberEmail: email,
                phoneNumber: phone,
                joinDate,
                role,
                background
             }, (error) => {
             saveButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');           
        });

        this.dataStore.set('member', updatedMember);

        saveButton.innerText = origButtonText;
        window.location.href = `/index.html`;
    }
}

     /**
     * Main method to run when the page contents have loaded.
     */
    const main = async () => {
    const updateMember = new UpdateMember();
   updateMember.mount();
    };

    window.addEventListener('DOMContentLoaded', main);
