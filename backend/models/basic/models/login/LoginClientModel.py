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

# Example Usage
# jsonstring = json.loads(myjsonstring)
# root = Root.from_dict(jsonstring)
