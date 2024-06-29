from typing import Optional
import uuid
from pydantic import BaseModel, Field

class PoliceLogin(BaseModel):
    id: str = Field(default_factory=uuid.uuid4, alias="_id")
    email: Optional[str] = Field(None, ...)
    username: Optional[str] = Field(None, ...)
    phone: Optional[str] = Field(None, ...)
    password: Optional[str] = Field(None, ...)

class UpdatePoliceLogin(BaseModel):
    email: Optional[str]
    username: Optional[str]
    phone: Optional[str]
    password: Optional[str]

    class Config:
        schema_extra = {
            "example": {
                "email": "demo@gmail.com",
                "username": "demo",
                "phone": "1234567890",
                "password": "password",
            }
        }
