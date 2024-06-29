from fastapi import APIRouter, Depends
from fastapi.responses import JSONResponse
from fastapi.encoders import jsonable_encoder

from ..middlewares.auth_handler import JWTBearer
from .v1.root import router as v1_router

# Routes

router = APIRouter()

@router.get("/", response_description="Api Version Manager route", dependencies=[Depends(JWTBearer())])
async def hello_world():
    return {
        "location" : "api/",
        "message" : "Hello World",
        "version" : "1.0.0",
        "status" : 200,
        "status_message" : "OK",
        "data" : {
            "message" : "Welcome to the API"
        }
    }



# ============================================================================
# Assigning Route Version to the Server  =====================================
# ============================================================================

router.include_router(v1_router, prefix="/v1", tags=["v1"])


# Include Future Versions Here
