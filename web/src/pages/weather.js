import TeamInSynchClient from '../api/teamInSynchClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the weather results page of the website.
 */
    class WeatherPage extends BindingClass {
       constructor() {
         super();
         this.bindClassMethods(['clientLoaded', 'mount', 'addWeatherToPage'], this);
         this.dataStore = new DataStore();
         this.dataStore.addChangeListener(this.addWeatherToPage);
         this.header = new Header(this.dataStore);
         console.log("WeatherPage constructor");
     }

    /**
    * Once the client is loaded, get the weather data.
    */
    async clientLoaded() {
            const urlParams = new URLSearchParams(window.location.search);
            const memberId = urlParams.get('id');
            document.getElementById('weather-results-container').innerText = "Loading...";
            try {
                const weatherData = await this.client.getWeather(memberId);
                this.dataStore.set('weatherData', weatherData);
            } catch (error) {
                console.error('Failed to load weather data:', error);
                document.getElementById('weather-results-container').innerText = 'Failed to load weather data.';
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
            * When the weather data is updated in the datastore, update the weather details on the page.
             */
                addWeatherToPage() {
                    const weatherData = this.dataStore.get('weatherData');
                    if (weatherData == null) {
                        return;
                    }

                    const weatherResultsContainer = document.getElementById('weather-results-container');
                     weatherResultsContainer.innerHTML = `
                     <p>Date: ${weatherData.weather.time}</p>
                     <p>Max Temperature: ${weatherData.weather.maxTemperature}°C</p>
                     <p>Min Temperature: ${weatherData.weather.minTemperature}°C</p>
                     <p>Weather Description: ${weatherData.weather.weatherDescription}</p>
                     `;
                }
         }

         /**
          * Main method to run when the page contents have loaded.
          */
         const main = async () => {
           const weatherPage = new WeatherPage();
             weatherPage.mount();
         };

         window.addEventListener('DOMContentLoaded', main);