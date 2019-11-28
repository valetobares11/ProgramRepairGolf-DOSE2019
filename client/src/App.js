import React, { Component } from 'react';
import UsersContainer from './components/componentsUser/UsersContainer';
import User from './components/componentsUser/User';
import { Route, BrowserRouter, Switch } from 'react-router-dom'
import Home from './components/componentsUser/Home'
import About from './components/componentsUser/About'
import Navbar from './components/componentsUser/Navbar'

class App extends Component {
  render() {
    return (
      <BrowserRouter>
        <div className="App">
          <Navbar />

          <Switch>
            <Route exact path='/' component={Home}/>
            <Route path='/about' component={About} />
            <Route path='/users' component={UsersContainer} />
          </Switch>
        </div>
      </BrowserRouter>
    );
  }
}

export default App
