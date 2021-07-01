import React, { useState, useEffect } from "react"
import AnimalTile from "./AnimalTile.js"
import { Redirect } from "react-router-dom"

const HappyAdoptedAnimals = props => {
  const [animalType, setAnimalType] = useState([])
  const [redirect, setRedirect] = useState(false)

  const fetchAnimalType = async () => {
    try {
      const response = await fetch(`/api/v1/pets/adopted`)
      if (!response.ok && response.status != 404) {
        const errorMessage = `${response.status} (${response.statusText})`
        const error = new Error(errorMessage)
        throw error
      } else if (response.status == 404) {
        setRedirect(true) 
      } else {
        const fetchedData = await response.json()
        setAnimalType(fetchedData)
      }  
    } catch (error) {
      console.error(error)
    }
  }

  useEffect(() => {
    fetchAnimalType()
  }, [])

  const petList = animalType.map(animal => {
    return (
      <AnimalTile
        key={animal.id}
        id={animal.id}
        name={animal.name}
        age={animal.age}
        vaccinationStatus={animal.vaccinationStatus}
        imgUrl={animal.imgUrl}
        type={props.match.params.type}
        adoptionStatus={animal.adoptionStatus}
      />
    )
  })

  if (redirect) {
    return <Redirect to="/404" />
  }

  return <div>{petList}<br/><br/></div>
}

export default HappyAdoptedAnimals