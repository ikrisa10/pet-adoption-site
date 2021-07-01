import React, { useState, useEffect } from "react"
import { Redirect } from "react-router-dom"
import Error from "./Error"

const AdminAdoptionReview = props => {
  const [animal, setAnimal] = useState([])
  const [redirect404, setRedirect404] = useState(false)
  const [redirect, setRedirect] = useState(false)
  const [adminForm, setAdminForm] = useState({
    applicationStatus: "",
    adminComments: ""
  })
  const [errors, setErrors] = useState([])
  const [applicationInfo, setApplicationInfo] = useState({
    name: "",
    phoneNumber: "",
    email: "",
    homeStatus: ""
  })

  const applicationId = props.match.params.applicationId
  const animalId = props.match.params.id

  const getAnimal = async () => {
    try {
      const response = await fetch(`/api/v1/pets/all/${animalId}`)
      if (!response.ok && response.status != 404) {
        const errorMessage = `${response.status} (${response.statusText})`
        const error = new Error(errorMessage)
        throw error
      } else if (response.status == 404) {
        setRedirect404(true)
      } else {
        const responseBody = await response.json()
        setAnimal(responseBody)
      }
    } catch (err) {
      console.error(`Error in Fetch Animal: ${err.message}`)
    }
  }

  const getFormData = async () => {
    try {
      const response = await fetch(`/api/v1/application/${applicationId}`)
      if (!response.ok) {
        const errorMessage = `${response.status} (${response.statusText})`
        const error = new Error(errorMessage)
        throw error
      } else {
        const responseBody = await response.json()
        setApplicationInfo(responseBody)
      }
    } catch (err) {
      console.error(`Error in Fetch From Data: ${err.message}`)
    }
  }

  const updateApplication = async () => {
    try {
      const response = await fetch(`/api/v1/application/update/${animalId}/${applicationId}`, {
        method: "POST",
        headers: new Headers({
          "Content-Type": "application/json"
        }),
        body: JSON.stringify(adminForm)
      })
      if (!response.ok) {
        const errorMessage = `${response.status} (${response.statusText})`
        const error = new Error(errorMessage)
        throw error 
      } else {
        setRedirect(true)
      }
    } catch (error) {
      console.error(`Error in fetch Update: ${error.message}`)
    }
  }

  useEffect(() => {
    getAnimal()
    getFormData()
  }, [])

    const isFormComplete = () => {
    let submitErrors = {}
    const requiredFields = ["applicationStatus", "adminComments"]
    requiredFields.forEach(field => {
      if (adminForm[field].trim() === "") {
        submitErrors = {...submitErrors, [field]: "is required." }
      }
    })
    setErrors(submitErrors)
    return _.isEmpty(submitErrors)
  }

  const handleChange = event => {
    setAdminForm({
      ...adminForm,
      [event.currentTarget.name]: event.currentTarget.value
    })
  }

  const handleSubmit = event => {
    event.preventDefault()
    if (isFormComplete()) {
      updateApplication()
    }
  }

  let vaccinated
  if (animal.vaccinationStatus) {
    vaccinated = "Yes"
  } else {
    vaccinated = "No"
  }

  if (redirect404) {
    return <Redirect to="/404" />
  }

  if (redirect) {
    return <Redirect to="/pending_applications"/>
  }

  return (
    <div>
      <div className="animal-show">
        <h1>{animal.name}</h1>
        <img className="images thumbnail" src={animal.imgUrl}></img>
        <ul className="no-bullet">
          <li><strong>Age:</strong> {animal.age} months old</li>
          <li>
            <strong>{animal.name}'s story:</strong> {animal.adoptionStory}
          </li>
          <li>
            <strong>Vaccination Status:</strong> {vaccinated}
          </li>
        </ul>
        <div>
          <h2>Applicant Info:</h2>
          <p>
            Name: {applicationInfo.name}<br/>
            Phone: {applicationInfo.phoneNumber}<br/>
            Email: {applicationInfo.email}<br/>
            Home Status: {applicationInfo.homeStatus}<br/>
          </p>
        </div>
        <form className="adoption_app" onSubmit={handleSubmit}> 
          <div>
            <Error errors={errors} />
          </div>
          <label htmlFor="applicationStatus">
              Admin Decision:
              <select
                id="applicationStatus"
                name="applicationStatus"
                onChange={handleChange}
                value={adminForm.applicationStatus}
              >
                <option value="">Please Select</option>
                <option value="approved">Approved</option>
                <option value="denied">Denied</option>
              </select>
          </label>
          <label htmlFor="adminComments">
            Please provide comments on your decision:
            <textarea id="adminComments" name="adminComments" onChange={handleChange} value={adminForm.adminComments} placeholder="Type comments here..."></textarea>
          </label>
          <input className="button" type="submit" value="Process" />
        </form>
      </div>
      <br/>
      <br/>
      <br/>
    </div>
  )
}

export default AdminAdoptionReview 