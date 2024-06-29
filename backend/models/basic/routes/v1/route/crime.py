from fastapi import APIRouter, Body, Request, HTTPException, status , UploadFile, File
from fastapi.responses import JSONResponse
from fastapi.encoders import jsonable_encoder
from typing import List

from ....models.login.LoginClientModel import Root as ClientLoginModel

# Routes

router = APIRouter()

# http://localhost:8000/api/v1/login/client
# http://localhost:8000/api/v1/login/manager
# http://localhost:8000/api/v1/login/admin

@router.get("/", response_description="Crime Main Route")
async def login():
    return {
        "location" : "api/v1/crime",
        "message" : "Crime Route Root Working Corrrectly",
        "version" : "1.0.0",
        "status" : 200,
        "status_message" : "OK",
        "data" : {
            "message" : "Welcome to Crime Route API"
        }
    }

@router.post("/register", response_description="Crime Register Route")
async def crimeRegister(request: Request, crime: dict = Body(...), images: List[UploadFile] = File(...)):


    # Validate the crime data
    if not isinstance(crime.get("crimeType"), str):
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid crime type")
    if not isinstance(crime.get("crimeDesc"), str):
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid crime description")
    if not isinstance(crime.get("crimeImages"), list) or len(crime.get("crimeImages")) > 3:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid crime images")
    if not all(isinstance(image, str) for image in crime.get("crimeImages")):
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid image format")
    if not isinstance(crime.get("crimeLong"), int) or not isinstance(crime.get("crimeLat"), int):
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid crime location")
    if not isinstance(crime.get("anonymous"), bool):
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid anonymous flag")
    if not isinstance(crime.get("userId"), int):
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid user ID")

    crimes_collection = request.app.firestore_db.collection("crimes")

    # Add the crime to the database
    result = crimes_collection.add(crime)

    return {
        "location" : "api/v1/crime/register",
        "message" : "Register Crime Route Root Working Corrrectly",
        "version" : "1.0.0",
        "status" : 200,
        "status_message" : "OK",
        "data" : {
            "message" : "Welcome to Manager Crime Route API"
        }
    }


@router.post("/solve", response_description="Solved Route")
async def crimeSolve(request: Request, crime_id: str = Body(...)):

    # Validate crime_id
    if not crime_id:
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Crime ID is required")

    # Update Firestore document
    crime_ref = firestore_db.collection("crimes").document(crime_id)
    crime_data = {
        "solved": True,
        "approved": False
    }
    crime_ref.update(crime_data)

    return {
        "location": "/api/v1/login/admin",
        "message": "Solve Crime Route Root Working Correctly",
        "version": "1.0.0",
        "status": 200,
        "status_message": "OK",
        "data": {
            "message": "Crime marked as solved",
            "crime_id": crime_id
        }
    }

@router.get("/list", response_description="List of Crimes")
async def list_crimes(request: Request):
    crimes_collection = request.app.firestore_db.collection("crimes")
    crimes = crimes_collection.get()

    crimes_list = []
    for crime in crimes:
        crime_data = crime.to_dict()
        crime_data['id'] = crime.id  # Add document ID to the data
        crimes_list.append(crime_data)

    return {
        "location": "api/v1/crimes/list",
        "message": "Crime List Route Working Correctly",
        "version": "1.0.0",
        "status": 200,
        "status_message": "OK",
        "data": crimes_list
    }


    # return {
    #     "location" : "api/v1/login/admin",
    #     "message" : " Solve Crime Route Root Working Corrrectly",
    #     "version" : "1.0.0",
    #     "status" : 200,
    #     "status_message" : "OK",
    #     "data" : {
    #         "message" : "Welcome to Admin Login Route API"
    #     }
    # }

# Crime Routes for Admin

@router.post("/approval", response_description="Solve Approval Route")
async def crimeApprovalAdmin(request: Request, crime: dict = Body(...)):
    return {
        "location" : "api/v1/crime/approval",
        "message" : " Solve Crime Route Root Working Corrrectly",
        "version" : "1.0.0",
        "status" : 200,
        "status_message" : "OK",
        "data" : {
            "message" : "Welcome to Admin Login Route API"
        }
    }

@router.post("/remove", response_description="Solved Route")
async def crimeRemoveAdmin(request: Request, crime: dict = Body(...)):
    return {
        "location" : "api/v1/crime/remove",
        "message" : " Admin Login Route Root Working Corrrectly",
        "version" : "1.0.0",
        "status" : 200,
        "status_message" : "OK",
        "data" : {
            "message" : "Welcome to Admin Login Route API"
        }
    }
