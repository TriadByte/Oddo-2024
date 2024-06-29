from pydantic import BaseModel
from typing import List

class AllowedAccess(BaseModel):
    allowed_domains: List[str]
    allowed_ips: List[str]
    description: str = "Allowed domains and IP addresses for the application"
    blocked_ips: List[str] = []
    blocked_domains: List[str] = []
