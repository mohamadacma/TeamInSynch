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
        this.bindClassMethods(['clientLoaded', 'mount', 'addWeatherToPage', 'getWeatherIconClass'], this);
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
        const container = document.getElementById('weather-results-container');
            if (!container) {
                console.error('Weather results container not found');
                return;
            }
            container.innerHTML = '';
        let weatherIcon = container.querySelector('.weather-icon');
            if (!weatherIcon) {
                weatherIcon = document.createElement('div');
                weatherIcon.className = 'weather-icon';
                container.appendChild(weatherIcon);
            }
        let weatherDetails = container.querySelector('.weather-details');
            if (!weatherDetails) {
                weatherDetails = document.createElement('div');
                weatherDetails.className = 'weather-details';
                container.appendChild(weatherDetails);
            }

        if (!weatherIcon || !weatherDetails) {
            console.error('Weather icon or details element not found');
            return;
        }

        const iconClass = this.getWeatherIconClass(weatherData.weather.weatherDescription);
        weatherIcon.innerHTML = `<i class="${iconClass}"></i>`;

        weatherDetails.innerHTML = `
            <h3>${weatherData.weather.weatherDescription}</h3>
            <p><i class="fas fa-calendar-day"></i> ${weatherData.weather.time}</p>
            <p><i class="fas fa-temperature-high"></i> Max: ${weatherData.weather.maxTemperature}°C</p>
            <p><i class="fas fa-temperature-low"></i> Min: ${weatherData.weather.minTemperature}°C</p>
        `;

        // Add animation class
        weatherIcon.classList.add('weather-icon-animate');
    }

    getWeatherIconClass(description) {
        const lowerDesc = description.toLowerCase();
        if (lowerDesc.includes('rain')) return 'fas fa-cloud-rain';
        if (lowerDesc.includes('cloud')) return 'fas fa-cloud';
        if (lowerDesc.includes('sun') || lowerDesc.includes('clear')) return 'fas fa-sun';
        if (lowerDesc.includes('snow')) return 'fas fa-snowflake';
        if (lowerDesc.includes('thunder')) return 'fas fa-bolt';
        return 'fas fa-cloud-sun'; // default icon
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