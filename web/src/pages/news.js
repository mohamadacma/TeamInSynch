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
     console.log('Received news data:', newsData);
    this.dataStore.set('newsData', newsData);
    console.log(newsData);
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
    if (!newsData || !newsData.newsModel) {
           console.error('Invalid news data:', newsData);
           document.getElementById('news-results-container').innerText = 'Failed to load news data.';
           return;
       }

      const newsResultsContainer = document.getElementById('news-results-container');
      newsResultsContainer.innerHTML = '';

      // access the sources (us,fr,ca..)
          const englishIndices = newsData.newsModel.sources
      //create a map that returns an array of null values for non-english sources and index for en sources
              .map((source, index) => (source === 'us' ? index : null))
       // another array created here without the null values from the map array
              .filter(index => index !== null);

      // Display headlines
      englishIndices.forEach((index) => {
       const headline = newsData.newsModel.headlines[index];
          const newsItem = `
              <div class="news-item">
                  <h3>${headline}</h3>
              </div>
          `;
          newsResultsContainer.innerHTML += newsItem;
      });

      // Display sources
      englishIndices.forEach((index) => {
                  const source = newsData.newsModel.sources[index];
                  const newsItem = `
                      <div class="news-item">
                          <p>Source: ${source}</p>
                      </div>
                  `;
                  newsResultsContainer.innerHTML += newsItem;
              });

      // Display URLs
        englishIndices.forEach((index) => {
            const url = newsData.newsModel.urls[index];
            const newsItem = `
                <div class="news-item">
                    <p><a href="${url}" target="_blank">Read more</a></p>
                </div>
            `;
            newsResultsContainer.innerHTML += newsItem;
        });

      // Display publish dates
         englishIndices.forEach((index) => {
             const publishDate = newsData.newsModel.publishDates[index];
             const newsItem = `
                 <div class="news-item">
                     <p>Published Date: ${publishDate}</p>
                 </div>
             `;
             newsResultsContainer.innerHTML += newsItem;
         });

      // Display images
         englishIndices.forEach((index) => {
             const image = newsData.newsModel.images[index];
             const newsItem = `
                 <div class="news-item">
                     <img src="${image}" alt="News Image">
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


