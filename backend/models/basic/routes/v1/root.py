from fastapi import APIRouter, Depends, Body, Request, HTTPException, status
from fastapi.responses import JSONResponse
from fastapi.encoders import jsonable_encoder
from .route.login import router as login_router
from .route.signup import router as signup_router
from .route.crime import router as crime_router

from ...middlewares.auth_handler import JWTBearer
import requests
import re
import random

# Routes

router = APIRouter()

@router.get("/", response_description="Hello World", dependencies=[Depends(JWTBearer())])
async def hello_world():
    return {
        "location" : "api/v1/",
        "message" : "Hello World",
        "version" : "1.0.0",
        "status" : 200,
        "status_message" : "OK",
        "data" : {
            "message" : "Welcome to the API"
        }
    }


@router.get("/jwt", response_description="JWT Route")
async def jwt(request: Request):
    return {
        "token": request.state.token
    }

@router.post("/otp", response_description="OTP Route")
async def otp(request: Request, otp: dict = Body(...)):
    try :
        email = otp["email"]
        message = otp["message"]
        otp = random.randint(1000, 9999)

        if not all(key in otp for key in ["email", "message"]):
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid OTP data")

        if not email:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Email is required")

        if not message:
            raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Message is required")

        if email:
            # Validate email format
            email_regex = r'^[\w\.-]+@[\w\.-]+\.\w+$'
            if not re.match(email_regex, email):
                raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Invalid email format")

        # Send OTP to email
        payload = {
            "to": email,
            "recEmail": email,
            "subj": message,
            "msg": "Your OTP is " + str(otp),
            "html": """<html><body><h1>Your OTP is """ + str(otp) + """</h1></body></html>"""
        }

        response = requests.post("https://api.triadbyte.com/mail/send/triadbytenoreply/", json=payload)
        if response.status_code == 200:
            return {
                "status": 200,
                "message": "OTP sent successfully",
                "data": {
                    "otp": otp
                }
            }
        else:
            return {
                "status": 400,
                "message": "Failed to send OTP",
                "data": {}
            }

    except Exception as e:
        return {
            "status": 400,
            "message": "Failed to send OTP",
            "data": {}
        }


router.include_router(login_router, prefix="/login", tags=["login"])
router.include_router(signup_router, prefix="/signup", tags=["signup"])
router.include_router(crime_router, prefix="/crime", tags=["crime"])

# router.include_router(signup_router, prefix="/signup", tags=["signup"])
# router.include_router(profile_router, prefix="/profile", tags=["signup"])




# @router.post("/", response_description="Add new task")
# async def create_task(request: Request, task: TaskModel = Body(...)):
#     task = jsonable_encoder(task)
#     new_task = await request.app.mongodb["tasks"].insert_one(task)
#     created_task = await request.app.mongodb["tasks"].find_one(
#         {"_id": new_task.inserted_id}
#     )

#     return JSONResponse(status_code=status.HTTP_201_CREATED, content=created_task)


# @router.get("/", response_description="List all tasks")
# async def list_tasks(request: Request):
#     tasks = []
#     for doc in await request.app.mongodb["tasks"].find().to_list(length=100):
#         tasks.append(doc)
#     return tasks


# @router.get("/{id}", response_description="Get a single task")
# async def show_task(id: str, request: Request):
#     if (task := await request.app.mongodb["tasks"].find_one({"_id": id})) is not None:
#         return task

#     raise HTTPException(status_code=404, detail=f"Task {id} not found")


# @router.put("/{id}", response_description="Update a task")
# async def update_task(id: str, request: Request, task: UpdateTaskModel = Body(...)):
#     task = {k: v for k, v in task.dict().items() if v is not None}

#     if len(task) >= 1:
#         update_result = await request.app.mongodb["tasks"].update_one(
#             {"_id": id}, {"$set": task}
#         )

#         if update_result.modified_count == 1:
#             if (
#                 updated_task := await request.app.mongodb["tasks"].find_one({"_id": id})
#             ) is not None:
#                 return updated_task

#     if (
#         existing_task := await request.app.mongodb["tasks"].find_one({"_id": id})
#     ) is not None:
#         return existing_task

#     raise HTTPException(status_code=404, detail=f"Task {id} not found")


# @router.delete("/{id}", response_description="Delete Task")
# async def delete_task(id: str, request: Request):
#     delete_result = await request.app.mongodb["tasks"].delete_one({"_id": id})

#     if delete_result.deleted_count == 1:
#         return JSONResponse(status_code=status.HTTP_204_NO_CONTENT)

#     raise HTTPException(status_code=404, detail=f"Task {id} not found")
