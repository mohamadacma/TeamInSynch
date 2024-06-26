import TeamInSynchClient from '../api/teamInSynchClient';
import BindingClass from "../util/bindingClass";

/**
 * The header component for the website.
 */
export default class Header extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'addHeaderToPage', 'createSiteTitle', 'createUserInfoForHeader'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new TeamInSynchClient();
    }

    /**
     * Add the header to the page.
     */
    async addHeaderToPage() {
        const currentUser = await this.client.getIdentity();

        const siteTitle = this.createSiteTitle();
        const userInfo = this.createUserInfoForHeader(currentUser);

        const header = document.getElementById('header');
        header.appendChild(siteTitle);
        const userInfoContainer = document.querySelector('.user-info-container');
         if (userInfoContainer) {
                    userInfoContainer.appendChild(userInfo);
                } else {
                    console.error('User info container not found');
                }
    }

    createSiteTitle() {
        const homeButton = document.createElement('a');
        homeButton.classList.add('header_home');
        homeButton.href = 'index.html';
        homeButton.innerText = '';

        const siteTitle = document.createElement('div');
        siteTitle.classList.add('site-title');
        siteTitle.appendChild(homeButton);

        return siteTitle;
    }

    createUserInfoForHeader(currentUser) {
        const userInfo = document.createElement('div');
        userInfo.classList.add('user-info');

        if (currentUser) {
                    const firstName = currentUser.name.split(' ')[0];
                    const logoutButton = this.createLogoutButton(firstName);
                    userInfo.appendChild(logoutButton);

                } else {
                    const loginButton = this.createLoginButton();
                    userInfo.appendChild(loginButton);
                }


        return userInfo;
    }

    createLoginButton() {
        return this.createButton('Login', this.client.login);
    }

    createLogoutButton(firstName) {
        return this.createButton(`Logout: ${firstName}`, this.client.logout);


    }

    createButton(text, clickHandler) {
        const button = document.createElement('a');
        button.classList.add('button');
        button.href = '#';
        button.innerText = text;

        button.addEventListener('click', async (event) => {
            event.preventDefault();
            await clickHandler();
        });

        return button;
    }
}
