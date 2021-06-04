import React, { useState } from 'react';
import axios from 'axios';
import Select from 'react-dropdown-select';

const ContentArea = () => {

    const [email, setEmail] = useState("");
    const [emailLocal, setEmailLocal] = useState("");
    const [pinCode, setPincode] = useState("");
    const [pinCodeLocal, setPincodeLocal] = useState("");
    const [age, setAge] = useState("18");
    const [isNotified, setIsNotified] = useState(false);
    const [isExist, setIsExist] = useState(false);

    const handleSubmit = (e) => {
        e.preventDefault();
        registerNotification();
    }

    const options = [
        {
            value: "18",
            label: "Age 18+"
        },
        {
            value: "45",
            label: "Age 45+"
        }];

    async function registerNotification() {
        const url = 'http://localhost:8080/notify';
        await axios.post(url, { email, pinCode, age })
            .then((c) => {
                setIsNotified(true);
                setIsExist(false);
                setEmailLocal(email);
                setPincodeLocal(pinCode);
                setEmail("");
                setPincode("");
            })
            .catch(
                (err) => {
                    setIsNotified(false);
                    setIsExist(true);
                }
            );
    }

    function DisplayNotification(props) {
        if (props.isNotified && props.isExist === false) {
            return <div
                className="container"
                style={{ fontSize: 25, width: 1100, color: "darkblue", fontWeight: "bold" }}>
                {"Registered on " + emailLocal + " & PIN " + pinCodeLocal + " for the Age " + age + "+"}
            </div>
        } else if (props.isNotified === false && props.isExist === true) {
            return <div className="container"
                style={{ fontSize: 25, width: 1100, color: "red", fontWeight: "bold" }}>
                {"Alert: You have already registered on " + emailLocal + " & PIN " + pinCodeLocal + " for the Age " + age + "+"}
            </div>
        } else {
            return <div />
        }
    }

    const validatePin = () => email.length > 0 && pinCode.length === 6 ? true : false;

    return (
        <React.Fragment>
            <div>
                <div className="row justify-content-center align-items-center">
                    <div className="jumbotron"
                        style={{ width: 400, fontSize: 20 }}>
                        <form
                            onSubmit={handleSubmit}>
                            <div
                                className="container">
                                <input
                                    type="text"
                                    name="email"
                                    id="email"
                                    placeholder="Email..."
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    style={{ width: 400, color: "blue" }}>
                                </input>
                            </div>
                            <br />
                            <div className="container">
                                <input
                                    type="text"
                                    name="pinCode"
                                    id="pinCode"
                                    placeholder="PIN Code..."
                                    value={pinCode}
                                    onChange={(e) => setPincode(e.target.value)}
                                    style={{ width: 400, color: "blue" }}>
                                </input>
                            </div>
                            <br />
                            <div className="container">
                                <Select
                                    multiple={false}
                                    placeholder="Age..."
                                    onChange={(values) => setAge(values[0].value)}
                                    options={options}
                                    dropdownPosition="auto"
                                    style={{ width: 400, color: "blue", backgroundColor: "white" }}
                                />
                            </div>
                            <br />
                            <div>
                                <input
                                    type="submit"
                                    name="submit"
                                    disabled={!validatePin()}
                                    className="btn btn-danger btn-md m-1"
                                    value="Notify Me"
                                    style={{ fontSize: 30, fontWeight: "bold" }}>
                                </input>
                            </div>
                        </form>
                        <br />
                    </div>
                    <div>
                        {
                            <DisplayNotification isNotified={isNotified} isExist={isExist} />
                        }
                    </div>
                </div>
            </div>
        </React.Fragment>
    );
}

export default ContentArea;