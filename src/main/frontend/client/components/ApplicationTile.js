import React, { useState } from "react"
import { Link, Redirect } from "react-router-dom"

const ApplicationTile = props => {
  const [redirect, setRedirect] = useState(false)

  const deleteApplication = async () => {
    try {
      const response = await fetch(`/api/v1/delete/${props.applicationId}`, {
        method: "DELETE"
      })
      if (!response.ok) {
        const errorMessage = `${response.status} (${response.statusText})`
        const error = new Error(errorMessage)
        throw error
      } else {
        setRedirect(true)
      }
    } catch (error) {
      console.error(`Error in fetch: ${error.message}`)
    }
  }

  const handleDelete = event => {
    event.preventDefault()
    if (confirm("Are you sure you want to delete this record?")) {
      deleteApplication()
    } else {
      alert("Okay, deletion aborted")
    }
  }

  if (redirect) {
    return <Redirect to="/pending_applications" />
  }

  return (
    <div className="cell">
      <div className="card">
        <div>
          <h3>{props.name}'s Adoption Application</h3>
        </div>
        <div className="card-section">
          <p className="h4">
            <strong>Pet Name:</strong> {props.adoptablePet.name}
            <br />
          </p>
        </div>
        <div className="button-group align-spaced">
          <button type="button" className="alert button" onClick={handleDelete}>
            Delete
          </button>
          <Link
            to={`/pending_applications/${props.applicationId}/${props.adoptablePet.id}`}
            className="success button"
          >
            Edit
          </Link>
          <Link
            to={`/pending_applications/admin/${props.applicationId}/${props.adoptablePet.id}`}
            className="button"
          >
            Admin Only
          </Link>
        </div>
      </div>
    </div>
  )
}
export default ApplicationTile
