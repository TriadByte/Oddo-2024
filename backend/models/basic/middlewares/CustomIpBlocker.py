from starlette.middleware.base import BaseHTTPMiddleware
from fastapi import FastAPI, Request, HTTPException
from starlette.responses import JSONResponse

# Custom Middleware for IP Blocking
class CustomIPBlockMiddleware(BaseHTTPMiddleware):
    def __init__(self, app: FastAPI, allowed_ips: list[str]):
        super().__init__(app)
        self.allowed_ips = allowed_ips

    async def dispatch(self, request: Request, call_next):
        client_ip = request.client.host

        if client_ip not in self.allowed_ips:
            return JSONResponse(status_code=403, content={"detail": "IP not allowed"})

        response = await call_next(request)
        return response