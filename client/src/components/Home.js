import React from 'react'
import CreateAccount from "./componentesUser/CreateAccount"
const Home = (props) => {
    return (
        <div>
        <div className="container">
            <h4 className="center">Home</h4>
            <p>Ingrese los datos</p>
            <CreateAccount/>
        </div>
        </div>
    )
}
export default Home;