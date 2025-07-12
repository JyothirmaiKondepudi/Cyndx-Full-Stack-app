FROM public.ecr.aws/lambda/python:3.12

WORKDIR /app

COPY requirements.txt ./

RUN pip install -r requirements.txt

COPY . .

EXPOSE 5000

CMD ["lambda_handler.lambda_handler"]