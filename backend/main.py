# Necessary imports
from fastapi import FastAPI
import uvicorn
from motor.motor_asyncio import AsyncIOMotorClient # type: ignore
import json
from datetime import datetime, timedelta
import jwt

# Importing Custom Access Models
from models.basic.models.AllowedAccess import AllowedAccess

# Importing Middlewares
from models.basic.middlewares.CustomIpBlocker import CustomIPBlockMiddleware
from starlette.middleware.base import BaseHTTPMiddleware
from fastapi.middleware.cors import CORSMiddleware

# Call Version Routes
from models.basic.routes.root import router as root_routes
from config import firestore_db, storage_bucket, settings

app = FastAPI()

# def create_access_token(data: dict, expires_delta: timedelta = None):
#     to_encode = data.copy()
#     if expires_delta:
#         expire = datetime.utcnow() + expires_delta
#     else:
#         expire = datetime.utcnow() + timedelta(minutes=15)
#     to_encode.update({"exp": expire})
#     encoded_jwt = jwt.encode(to_encode, SECRET_KEY, algorithm=ALGORITHM)
#     return encoded_jwt


# ============================================================================
# Default Allowed Origins and IPs ============================================
# ============================================================================

# Default allowed origins and IPs
ALLOWED_ORIGINS = [
    "http://localhost"
]

ALLOWED_IPS = [
    "127.0.0.1"
]


# ============================================================================
# Helper Functions ============================================================
# ============================================================================

# Function to load allowed access from JSON file
def load_allowed_access_from_json():
    with open('allowed_access.json', 'r') as file:
        data = json.load(file)
        return AllowedAccess(**data)


# ============================================================================
# Database Connection, Startup and Shutdown Handlers =========================
# ============================================================================

@app.on_event("startup")
async def startup():
    # Load allowed access details from JSON file
    allowed_access = load_allowed_access_from_json()
    print("domains : ", allowed_access)
    global ALLOWED_ORIGINS, ALLOWED_IPS
    ALLOWED_ORIGINS = allowed_access.allowed_domains
    ALLOWED_IPS = allowed_access.allowed_ips
    
        # Initialize Firestore
    app.firestore_db = firestore_db

    # Initialize Storage Bucket
    app.storage_bucket = storage_bucket

# ============================================================================
# Assigning Routes to the Server  ============================================
# ============================================================================

app.include_router(root_routes, tags=["/api"], prefix="/api")

# ============================================================================
# Assigning Middlewares to the Server ========================================
# ============================================================================

# Custom Middleware for IP Blocking
app.add_middleware(
    CustomIPBlockMiddleware,
    allowed_ips=ALLOWED_IPS
)

# CORS Middleware
app.add_middleware(
    CORSMiddleware,
    allow_origins=ALLOWED_ORIGINS,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


# ============================================================================
# Running the Server =========================================================
# ============================================================================

if __name__ == "__main__":
    uvicorn.run(
        "main:app",
        host=settings.HOST,
        reload=settings.DEBUG_MODE,
        port=settings.PORT,
    )
