import TeamInSynchClient from '../api/teamInSynchClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the news results page of the website.
 */
 class NewsPage extends BindingClass {
   constructor() {
     super();
     this.bindClassMethods(['clientLoaded', 'mount', 'addNewsToPage'], this);
     this.dataStore = new DataStore();
     this.dataStore.addChangeListener(this.addNewsToPage);
     this.header = new Header(this.dataStore);
     console.log("NewsPage constructor");
   }

   /**
   * Once the client is loaded, get the news data.
   */
    async clientLoaded() {
    const urlParams = new URLSearchParams(window.location.search);
    const memberId = urlParams.get('id');
    document.getElementById('news-results-container').innerText = "Loading...";
    try {
    const newsData = await this.client.getNews(memberId);
    this.dataStore.set('newsData', newsData);
     } catch (error) {
     console.error('Failed to load news data:', error);
     document.getElementById('news-results-container').innerText = 'Failed to load news data.';
           }
       }

          /**
           * Add the header to the page and load the TeamInSynchClient.
           */
              mount() {
                  this.header.addHeaderToPage();
                  this.client = new TeamInSynchClient();
                  this.clientLoaded();
              }

   /**
   * When the news data is updated in the datastore, update the news details on the page.
   */
   addNewsToPage() {
   const newsData = this.dataStore.get('newsData');
   if (newsData == null) {
    return;
   }

   const newsResultsContainer = document.getElementById('news-results-container');
   newsResultsContainer.innerHTML = '';

   newsData.news.headlines.forEach((headline, index) => {
     const newsItem = `
       <div class="news-item">
       <h3>${headline}</h3>
       <p>Source: ${newsData.news.sources[index]}</p>
       <p><a href="${newsData.news.URLs[index]}" target="_blank">Read more</a></p>
       <p>Published Date: ${newsData.news.publishDates[index]}</p>
       <img src="${newsData.news.images[index]}" alt="News Image">
       </div>
       `;
   newsResultsContainer.innerHTML += newsItem;
   });
 }
}

   /**
   * Main method to run when the page contents have loaded.
   */
   const main = async () => {
     const newsPage = new NewsPage();
     newsPage.mount();
   };

   window.addEventListener('DOMContentLoaded', main);


