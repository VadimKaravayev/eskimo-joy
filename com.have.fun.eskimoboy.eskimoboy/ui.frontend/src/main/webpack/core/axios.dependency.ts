import Axios from 'axios';

declare global {
    interface Window {
        Axios;
    }
}

window.Axios = Axios;
