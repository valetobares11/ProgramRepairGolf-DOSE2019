import React, { Component } from 'react';
import Hackers from './Hackers';
import Hacker from './components/Hacker';
import { Route, BrowserRouter, Switch } from 'react-router-dom'
import Home from './components/Home'
import About from './components/About'
import Navbar from './components/Navbar'
import axios from 'axios';

class App extends Component {
  state = {
    hackers: []
  }

  componentDidMount() {
    axios.get('https://swapi.co/api/people')
      .then(res => {
        console.log(res.daa);
        this.setState({
          hackers: res.data.results.slice(0,10)
        })
      })
  }

  addHacker = (hacker) => {
    hacker.id = Math.random();
    let hackers = [...this.state.hackers, hacker];
    this.setState({
      hackers: hackers
    });
  }

  deleteHacker = (id) => {
    let hackers = this.state.hackers.filter(hacker => {
      return hacker.id !== id
    });

    this.setState({
      hackers: hackers
    });
  }

  render() {
    return (
      <BrowserRouter>
        <div className="App">
          <Navbar />

          <Switch>
            <Route exact path='/' component={Home}/>
            <Route path='/about' component={About} />
            <Route path='/hackers' render={
              (props) =>
                <Hackers
                  hackers={this.state.hackers}
                  addHacker={this.addHacker}
                  deleteHacker={this.deleteHacker}
                />} />
            <Route path="/:hacker_id" component={Hacker} ></Route>
          </Switch>
        </div>
      </BrowserRouter>
    );
  }
}

export default App;
