import React from "react"
import { hot } from "react-hot-loader/root"
import { Route, Switch, BrowserRouter, Redirect } from "react-router-dom"
import "regenerator-runtime/runtime"

import TypeList from "./TypeList.js"
import AnimalList from "./AnimalList.js"
import AnimalShow from "./AnimalShow.js"
import SurrenderForm from "./SurrenderForm"
import SuccessTile from "./SuccessTile.js"
import NavBar from "./NavBar.js"
import FourOFourTile from "./404.js"
import ApplicationList from "./ApplicationList.js"
import AdoptionFormEdit from "./AdoptionFormEdit.js"
import AdminAdoptionReview from "./AdminAdoptionReview.js"
import HappyAdoptedAnimals from "./HappyAdoptedAnimals.js"

const App = props => {
  return (
    <BrowserRouter>
      <NavBar />
      <div className="grid-container no-bullet">
        <div className="text-center">
          <Switch>
            <Route exact path="/">
              <Redirect to="/pets" />
            </Route>
            <Route exact path="/pets" component={TypeList} />
            <Route exact path="/pets/happy_adopted_animals" component={HappyAdoptedAnimals} />
            <Route exact path="/pets/:type" component={AnimalList} />
            <Route exact path="/pets/:type/:id" component={AnimalShow} />
            <Route exact path="/adoptions" component={SuccessTile} />
            <Route exact path="/adoptions/new" component={SurrenderForm} />
            <Route exact path="/404" component={FourOFourTile} />
            <Route exact path="/pending_applications" component={ApplicationList} />
            <Route exact path="/pending_applications/:applicationId/:id" component={AdoptionFormEdit} />
            <Route exact path="/pending_applications/admin/:applicationId/:id" component={AdminAdoptionReview} />
          </Switch>
        </div>
        <footer className="footer">Adopt A Pet</footer>
      </div>
    </BrowserRouter>
  )
}
export default hot(App)
