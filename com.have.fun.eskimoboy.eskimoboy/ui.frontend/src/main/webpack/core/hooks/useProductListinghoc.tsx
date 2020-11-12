import { useEffect, useState} from 'react';


const capitalizeFirstLetter = (word: string) => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();


export const useProductListingHoc = () => {
    const [isPostCheckState, setAvailabilityCheckState] = useState(false);
    const localStorageData = JSON.parse(window.localStorage.getItem('availabilityCheckerData'));

    useEffect(() => {
        setAvailabilityCheckState(!!localStorage.getItem('availabilityCheckerData'));
    }, []);

    const getAvailableProductsSpeedDetails = () => {
        const { response: { ServiceAvailability: { Bundle: bundles } } } = localStorageData;

        return bundles?.map((bundle) => {
            const {
                BBFTTP: { Productcode: fttp, ONTType: ontType},
                BBSOGEA: { Productcode: sogea, AccessLineType: lineType }
            } = bundle;

            if (sogea) {
                const { SOGEASpeed: { lowDownloadSpeed, highDownloadSpeed, lowUploadSpeed, highUploadSpeed } } = bundle;

                return {
                    productCode: sogea,
                    accessTechnology: 'SOGEA',
                    lineType,
                    lowDownloadSpeed: Math.round(lowDownloadSpeed),
                    highDownloadSpeed: Math.round(highDownloadSpeed),
                    lowUploadSpeed: Math.round(lowUploadSpeed),
                    highUploadSpeed: Math.round(highUploadSpeed)
                };
            }

            if (fttp) {
                const fttpFields = ['FTTP80_20MSpeed', 'FTTP160_30MSpeed', 'FTTP330_50MSpeed'];
                let downloadSpeed = 0, uploadSpeed = 0;
                fttpFields.forEach(fttpField => {
                    const { highDownloadSpeed, highUploadSpeed} = bundle[fttpField];
                    downloadSpeed = highDownloadSpeed || downloadSpeed;
                    uploadSpeed = highUploadSpeed || uploadSpeed;
                });

                return {
                    productCode: fttp,
                    accessTechnology: 'FTTP',
                    lineType: ontType,
                    highDownloadSpeed: Math.round(downloadSpeed),
                    highUploadSpeed: Math.round(uploadSpeed)
                };
            }
            return {};
        });
    };

    const isAnyProductAvailableAfterAvailabilityCheck = (): boolean => {
        const { response: { ServiceAvailability: { Bundle: bundle } } } = localStorageData;
        return bundle?.length > 0;
    }

    const getProductsAfterAvailabilityCheck = (products) => {
        const availableProducts = getAvailableProductsSpeedDetails();

        return products
            .filter(product => availableProducts.some(availableProduct => availableProduct.productCode === product.productCode))
            .map(product => {
                product.speedAttributes = availableProducts.find(availableProduct => availableProduct.productCode === product.productCode);
                return product;
            })
    };

    const getAddress = () => {
        const { selectedAddress: { city, postcode, streetName, streetNr}} = localStorageData;

        return `${capitalizeFirstLetter(streetNr)} ${capitalizeFirstLetter(streetName)} St, ${capitalizeFirstLetter(city)} ${postcode}.`;
    };

    const getMaxSpeed = () => {
        let productWithMaxSpeed = { highDownloadSpeed: 0, lowDownloadSpeed: 0 };
        const availableProducts = getAvailableProductsSpeedDetails();

        availableProducts.forEach(item => {
            if (item.highDownloadSpeed > productWithMaxSpeed.highDownloadSpeed) {
                productWithMaxSpeed = item;
            }
        });

        return productWithMaxSpeed.lowDownloadSpeed 
            ? `range of ${productWithMaxSpeed.lowDownloadSpeed} - ${productWithMaxSpeed.highDownloadSpeed}`
            : `up to ${productWithMaxSpeed.highDownloadSpeed}`;
    };

    return {
        isPostCheckState,
        isAnyProductAvailableAfterAvailabilityCheck,
        getProductsAfterAvailabilityCheck,
        getAddress,
        getAvailableProductsSpeedDetails,
        getMaxSpeed
    };
};