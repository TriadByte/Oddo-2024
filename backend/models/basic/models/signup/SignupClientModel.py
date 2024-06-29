from typing import Any
from dataclasses import dataclass
import json

@dataclass
class Root:
    firstName: str
    middleName: str
    lastName: str
    email: str
    phoneNumber: str
    password: str
    cPassword: str

    @staticmethod
    def from_dict(obj: Any) -> 'Root':
        _firstName = str(obj.get("firstName"))
        _middleName = str(obj.get("middleName"))
        _lastName = str(obj.get("lastName"))
        _email = str(obj.get("email"))
        _phoneNumber = str(obj.get("phoneNumber"))
        _password = str(obj.get("password"))
        _cPassword = str(obj.get("cPassword"))
        return Root(_firstName, _middleName, _lastName, _email, _phoneNumber, _password, _cPassword)

    @staticmethod
    def to_dict(root: 'Root') -> Any:
        return {
            "firstName": root.firstName,
            "middleName": root.middleName,
            "lastName": root.lastName,
            "email": root.email,
            "phoneNumber": root.phoneNumber,
            "password": root.password
        }
    
    def to_dict(self) -> Any:
        return {
            "firstName": self.firstName,
            "middleName": self.middleName,
            "lastName": self.lastName,
            "email": self.email,
            "phoneNumber": self.phoneNumber,
            "type": 1,
            "password": self.password
        }

# Example Usage
# jsonstring = json.loads(myjsonstring)
# root = Root.from_dict(jsonstring)
