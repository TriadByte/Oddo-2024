import firebase_admin
from firebase_admin import credentials, firestore, storage
from pydantic_settings import BaseSettings

# Initialize Firebase Admin SDK
cred = credentials.Certificate("./config/crimesnap-b7a5c-firebase-adminsdk-f5dtx-0a4907f56d.json")
firebase_admin.initialize_app(cred, {
    'storageBucket': 'crimesnap-b7a5c.appspot.com'
})

# Firestore client
firestore_db = firestore.client()

# Storage bucket client
storage_bucket = storage.bucket()

class CommonSettings(BaseSettings):
    APP_NAME: str = "Odoo Hackathon"
    DEBUG_MODE: bool = False

class ServerSettings(BaseSettings):
    HOST: str = "0.0.0.0"
    PORT: int = 8000

class Settings(CommonSettings, ServerSettings):
    pass

settings = Settings()
