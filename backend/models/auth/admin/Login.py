from typing import Optional
import uuid
from pydantic import BaseModel, Field

class AdminLogin(BaseModel):
    id: str = Field(default_factory=uuid.uuid4, alias="_id")
    email: Optional[str] = Field(None, ...)
    username: Optional[str] = Field(None, ...)
    phone: Optional[str] = Field(None, ...)
    password: str = Field(..., ...)

class UpdateAdminLogin(BaseModel):
    email: Optional[str]
    username: Optional[str]
    phone: Optional[str]
    password: str

    class Config:
        schema_extra = {
            "example": {
                "email": "demo@gmail.com",
                "username": "demo",
                "phone": "1234567890",
                "password": "password",
            }
        }
