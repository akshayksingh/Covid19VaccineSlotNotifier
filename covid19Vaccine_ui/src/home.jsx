import React from 'react';
import ContentArea from './contentArea';
import Footer from './footer';
import Header from './header';
import './home.css';

const Home = () => {
    return (
        <React.Fragment>
            <div>
                <Header />
            </div>
            <br />
            <div>
                <ContentArea />
            </div>
            <br />
            <div>
                <Footer />
            </div>
        </React.Fragment>
    );
}

export default Home;