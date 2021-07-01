import React, { useState } from "react"
import _ from "lodash"

import Error from "./Error"

const AdoptionForm = props => {
  const [newAdoption, setNewAdoption] = useState({
    name: "",
    phoneNumber: "",
    email: "",
    homeStatus: ""
  })
  const [errors, setErrors] = useState([])

  const addNewApplication = async () => {
    let formPayload = newAdoption
    formPayload.adoptablePetId = props.id
    try {
      const response = await fetch("/api/v1/application", {
        method: "POST",
        headers: new Headers({
          "Content-Type": "application/json"
        }),
        body: JSON.stringify(formPayload)
      })
      if (!response.ok && response.status != 406) {
        const errorMessage = `${response.status} (${response.statusText})`
        const error = new Error(errorMessage)
        throw error
      } else if(response.status == 406) {
        const body = await response.json() 
        setErrors(body) 
      } else {
        const body = await response.json()
        if (body) {
          props.handleWhatToShow()
        }
      }
    } catch (error) {
      console.error(`Error in fetch: ${error.message}`)
    }
  }

  const isFormComplete = () => {
    let submitErrors = {}
    const requiredFields = ["name", "phoneNumber", "email", "homeStatus"]
    requiredFields.forEach(field => {
      if (newAdoption[field].trim() === "") {
        submitErrors = {...submitErrors, [field]: "is required." }
      }
    })
    setErrors(submitErrors)
    return _.isEmpty(submitErrors)
  }

  const handleChange = event => {
    setNewAdoption({
      ...newAdoption,
      [event.currentTarget.name]: event.currentTarget.value
    })
  }

  const handleSubmit = event => {
    event.preventDefault()
    if (isFormComplete()) {
      addNewApplication(newAdoption)
    }
  }

  return (
    <form onSubmit={handleSubmit} className="adoption_app">
      <div>
        <h2>Adopt me!</h2>
        <Error errors={errors} />
      </div>
      <div className="grid-container">
        <div className="grid-x grid-padding-x">
          <div className="medium-6 cell">
            <label htmlFor="name">
              Your name
              <input id="name" type="text" name="name" onChange={handleChange} value={newAdoption.name} />
            </label>
          </div>

          <div className="medium-6 cell">
            <label htmlFor="phoneNumber">
              Phone Number
              <input
                id="phoneNumber"
                type="text"
                name="phoneNumber"
                onChange={handleChange}
                value={newAdoption.phoneNumber}
              />
            </label>
          </div>



          <div className="medium-6 cell">
            <label htmlFor="email">
              Email Address
              <input
                id="email"
                type="text"
                name="email"
                onChange={handleChange}
                value={newAdoption.email}
              />
            </label>
          </div>

          <div className="medium-6 cell">
            <label htmlFor="homeStatus">
              Own or Rent Your Home?
              <select
                id="homeStatus"
                name="homeStatus"
                onChange={handleChange}
                value={newAdoption.homeStatus}
              >
                <option value="">Please Select</option>
                <option value="Own">Own</option>
                <option value="Rent">Rent</option>
              </select>
            </label>
          </div>
        </div>
      </div>
      <input className="button round" type="submit" value="Apply" />
    </form>
  )
}

export default AdoptionForm
