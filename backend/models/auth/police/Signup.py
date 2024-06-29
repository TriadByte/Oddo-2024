from typing import Optional
import uuid
from pydantic import BaseModel, Field

class PoliceSignup(BaseModel):
    id: str = Field(default_factory=uuid.uuid4, alias="_id")
    email: Optional[str] = Field(None, ...)
    phone: Optional[str] = Field(None, ...)
    firstName: Optional[str] = Field(None, ...)
    middleName: Optional[str] = Field(None, ...)
    lastName: Optional[str] = Field(None, ...)
    password: str = Field(..., ...)
    confirmPassword: str = Field(..., ...)

class UpdatePoliceSignup(BaseModel):
    email: Optional[str] 
    phone: Optional[str] 
    firstName: Optional[str]
    middleName: Optional[str]
    lastName: Optional[str]
    password: str 
    confirmPassword: str

    class Config:
        schema_extra = {
                "email": "demo@gmail.com",
                "phone": "1234567890",
                "firstName": "Vishal",
                "middleName": "Kumar",
                "lastName": "Gupta",
                "password": "12345XYZ",
                "confirmPassword": "12345XYZ"
        }
