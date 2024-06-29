from typing import Optional
import uuid
from pydantic import BaseModel, Field

class AdminProfile(BaseModel):
    id: str = Field(default_factory=uuid.uuid4, alias="_id")
    email: Optional[str] = Field(None, ...)
    username: Optional[str] = Field(None, ...)
    phone: Optional[str] = Field(None, ...)
    profilePic: Optional[str] = Field(None, ...)
    firstName: Optional[str] = Field(None, ...)
    middleName: Optional[str] = Field(None, ...)
    lastName: Optional[str] = Field(None, ...)
    marriedStatus: Optional[str] = Field(None, ...)
    address: Optional[str] = Field(None, ...)
    bloodGroup: Optional[str] = Field(None, ...)
    dateOfBirth: Optional[str] = Field(None, ...)
    city: Optional[str] = Field(None, ...)
    pincode: Optional[str] = Field(None, ...)
    state: Optional[str] = Field(None, ...)
    nationality: Optional[str] = Field(None, ...)

class UpdateAdminProfile(BaseModel):
    email: Optional[str] 
    username: Optional[str] 
    phone: Optional[str] 
    profilePic: Optional[str]
    firstName: Optional[str]
    middleName: Optional[str]
    lastName: Optional[str]
    marriedStatus: Optional[str]
    address: Optional[str]
    bloodGroup: Optional[str] 
    dateOfBirth: Optional[str]
    city: Optional[str] 
    pincode: Optional[str]
    state: Optional[str] 
    nationality: Optional[str]

    class Config:
        schema_extra = {
           "email": "demo@gmail.com",
                "username": "demo",
                "phone": "1234567890",
                "profilePic": "https://example.com/profile.jpg",
                "firstName": "Vishal",
                "middleName": "Kumar",
                "lastName": "Gupta",
                "marriedStatus": "Single",
                "address": "123 Main St",
                "bloodGroup": "O+",
                "dateOfBirth": "1990-01-01",
                "city": "New York",
                "pincode": 10001,
                "state": "NY",
                "nationality": "Indian"
                    
}
         
        
