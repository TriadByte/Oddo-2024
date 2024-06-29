from fastapi import APIRouter, Body, Request, HTTPException, status
from fastapi.responses import JSONResponse
from fastapi.encoders import jsonable_encoder

from ....models.login.LoginClientModel import Root as ClientLoginModel

# Routes

router = APIRouter()

# http://localhost:8000/api/v1/login/client
# http://localhost:8000/api/v1/login/manager
# http://localhost:8000/api/v1/login/admin

@router.get("/", response_description="Login Route")
async def login():
    return {
        "location" : "api/v1/login",
        "message" : "Login Route Root Working Corrrectly",
        "version" : "1.0.0",
        "status" : 200,
        "status_message" : "OK",
        "data" : {
            "message" : "Welcome to Login Route API"
        }
    }

@router.get("/police", response_description="Police Login Route")
async def policeLogin():
    return {
        "location" : "api/v1/login/police",
        "message" : "Police Login Route Root Working Corrrectly",
        "version" : "1.0.0",
        "status" : 200,
        "status_message" : "OK",
        "data" : {
            "message" : "Welcome to Manager Login Route API"
        }
    }

@router.get("/admin", response_description="Login Route")
async def adminLogin():
    return {
        "location" : "api/v1/login/admin",
        "message" : " Admin Login Route Root Working Corrrectly",
        "version" : "1.0.0",
        "status" : 200,
        "status_message" : "OK",
        "data" : {
            "message" : "Welcome to Admin Login Route API"
        }
    }
