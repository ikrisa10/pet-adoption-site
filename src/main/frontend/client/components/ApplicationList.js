import React, { useState, useEffect } from "react"
import ApplicationTile from "./ApplicationTile.js"
import { Redirect } from "react-router-dom"

const ApplicationList = props => {
  const [pendingApplications, setPendingApplications] = useState([])
  const [redirect, setRedirect] = useState(false)

  const fetchApplication = async () => {
    try {
      const response = await fetch(`/api/v1/pending_applications`)
      if (!response.ok && response.status != 404) {
        const errorMessage = `${response.status} (${response.statusText})`
        const error = new Error(errorMessage)
        throw error
      } else if (response.status == 404) {
        setRedirect(true) 
      } else {
        const fetchedData = await response.json()
        setPendingApplications(fetchedData)
      }  
    } catch (error) {
      console.error(error)
    }
  }

  useEffect(() => {
    fetchApplication()
  }, [])

  const applicationList = pendingApplications.map(application => {
    return (
      <ApplicationTile
        key={application.id}
        applicationId={application.id}
        name={application.name}
        phoneNumber={application.phoneNumber}
        email={application.email}
        homeStatus={application.homeStatus}
        applicationStatus={application.applicationStatus}
        adoptablePet={application.adoptablePet}
      />
    )
  })

  if (redirect) {
    return <Redirect to="/404" />
  }

  return <div>{applicationList}<br/><br/></div>
}

export default ApplicationList
