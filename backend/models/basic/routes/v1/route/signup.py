from fastapi import APIRouter, Body, Request, HTTPException, status
from fastapi.responses import JSONResponse
from fastapi.encoders import jsonable_encoder

from ....models.signup.SignupClientModel import Root as ClientSignupModel
from ....models.signup.SignupManagerModel import Root as ManagerSignupModel
from ....models.signup.SignupAdminModel import Root as AdminSignupModel
import re

# Routes

router = APIRouter()

@router.get("/", response_description="Signup Route")
async def login():
    return {
        "location" : "api/v1/signup",
        "message" : "Signup Route Root Working Corrrectly",
        "version" : "1.0.0",
        "status" : 200,
        "status_message" : "OK",
        "data" : {
            "message" : "Welcome to Signup Route API"
        }
    }

# Route to handle client signup
@router.post("/client", response_description="Signup Route")
async def client_signup(request: Request, client: dict = Body(...)):
    try:
        # Validate client data
        if not all(key in client for key in ["firstName", "lastName", "email", "phoneNumber", "password", "cPassword"]):
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid client data")

        # Check if email is already registered
        existing_user = await request.app.mongodb["users"].find_one({"email": client["email"]})
        if existing_user:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Email already registered")

        # Check if password and confirm password match
        if client["password"] != client["cPassword"]:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Password and confirm password do not match")

        # Check if firstName and lastName are not empty
        if not client["firstName"] or not client["lastName"]:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="First name and last name are required")

        # Check if email is valid
        email_pattern = r'^[\w\.-]+@[\w\.-]+\.\w+$'
        if not re.match(email_pattern, client["email"]):
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid email address")

        # Check if phoneNumber is valid
        phone_pattern = r'^\d{10}$'
        if not re.match(phone_pattern, client["phoneNumber"]):
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid phone number")

        # Check if password meets requirements
        if len(client["password"]) < 8:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Password must be at least 8 characters long")

        # Create ClientSignupModel instance and insert into Firestore

        client_model = ClientSignupModel(**client)

        await request.app.mongodb["users"].insert_one(client_model.dict())

        return {
            "status": 200,
            "message": "Signup Successful",
            "location": "/login/client",
        }
    except HTTPException as he:
        raise he
    except Exception as e:
        return {
            "status": 400,
            "message": "An error occurred while trying to signup"
        }

# Route to handle Manager signup
@router.post("/manager", response_description="Signup Route")
async def manager_signup(request: Request, manager: dict = Body(...)):
    try:
        # Validate client data
        if not all(key in manager for key in ["firstName", "lastName", "email", "phoneNumber", "password", "cPassword"]):
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid manager data")

        # Check if email is already registered
        existing_user = await request.app.mongodb["users"].find_one({"email": manager["email"]})
        if existing_user:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Email already registered")

        # Check if password and confirm password match
        if manager["password"] != manager["cPassword"]:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Password and confirm password do not match")

        # Check if firstName and lastName are not empty
        if not manager["firstName"] or not manager["lastName"]:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="First name and last name are required")

        # Check if email is valid
        email_pattern = r'^[\w\.-]+@[\w\.-]+\.\w+$'
        if not re.match(email_pattern, manager["email"]):
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid email address")

        # Check if phoneNumber is valid
        phone_pattern = r'^\d{10}$'
        if not re.match(phone_pattern, manager["phoneNumber"]):
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid phone number")

        # Check if password meets requirements
        if len(manager["password"]) < 8:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Password must be at least 8 characters long")

        # Create ManagerSignupModel instance and insert into MongoDB
        manager_model = ManagerSignupModel(**manager)
        
        await request.app.mongodb["users"].insert_one(manager_model.dict())

        return {
            "status": 200,
            "message": "Signup Successful",
            "location": "/login/manager",
        }
    except HTTPException as he:
        raise he
    except Exception as e:
        return {
            "status": 400,
            "message": "An error occurred while trying to signup"
        }


# Route to handle admin signup
@router.post("/admin", response_description="Signup Route")
async def admin_signup(request: Request, admin: dict = Body(...)):
    try:
        # Validate client data
        if not all(key in admin for key in ["firstName", "lastName", "email", "phoneNumber", "password", "cPassword"]):
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid admin data")

        # Check if email is already registered
        existing_user = await request.app.mongodb["superusers"].find_one({"email": admin["email"]})
        if existing_user:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Email already registered")

        # Check if password and confirm password match
        if admin["password"] != admin["cPassword"]:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Password and confirm password do not match")

        # Check if firstName and lastName are not empty
        if not admin["firstName"] or not admin["lastName"]:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="First name and last name are required")

        # Check if email is valid
        email_pattern = r'^[\w\.-]+@[\w\.-]+\.\w+$'
        if not re.match(email_pattern, admin["email"]):
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid email address")

        # Check if phoneNumber is valid
        phone_pattern = r'^\d{10}$'
        if not re.match(phone_pattern, admin["phoneNumber"]):
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid phone number")

        # Check if password meets requirements
        if len(admin["password"]) < 8:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Password must be at least 8 characters long")

        # Create AdminSignupModel instance and insert into MongoDB
        admin_model = AdminSignupModel(**admin)
        
        await request.app.mongodb["users"].insert_one(admin_model.dict())

        return {
            "status": 200,
            "message": "Signup Successful",
            "location": "/login/admin",
        }
    except HTTPException as he:
        raise he
    except Exception as e:
        return {
            "status": 400,
            "message": "An error occurred while trying to signup"
        }

