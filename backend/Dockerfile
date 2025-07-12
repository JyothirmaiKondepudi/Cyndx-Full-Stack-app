FROM python:3.12.11-slim

WORKDIR /app

COPY requirements.txt ./

RUN pip install awslambdaric && pip install -r requirements.txt

COPY . .

EXPOSE 5000

ENTRYPOINT ["awslambdaric"]
CMD ["lambda_handler.lambda_handler"]