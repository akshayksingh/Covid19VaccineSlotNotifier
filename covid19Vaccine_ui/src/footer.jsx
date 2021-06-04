import React from 'react';

const Footer = () => {
    return (
        <React.Fragment>
            <div className="navbar navbar-inverse fixed-bottom">
                <div className="container">
                    <p className="navbar-info">@Copy rights. Contact the developer {""}
                        <a href="https://www.linkedin.com/in/singh-akshay-kumar/">Akshay Kumar Singh</a></p>
                    <p className="navbar-info">Check the source code on {""}
                        <a href="https://github.com/akshayksingh/Covid19VaccineSlotNotifier">GitHub</a></p>
                    <p className="navbar-info">*Be assured, Your data is safe and will not be shared with anyone!!</p>
                </div>
            </div>
        </React.Fragment>
    );
}

export default Footer;