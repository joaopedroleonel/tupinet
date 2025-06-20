from google import genai
from flask import Flask, request, jsonify, Response
from google.genai import types
from flask_cors import CORS
import os
from dotenv import load_dotenv

load_dotenv()

app = Flask(__name__)
CORS(app)

@app.route('/', methods=['POST'])
def transcrever():
    try:
        if request.data:
            audio_bytes = request.data

            audio_part = types.Part.from_bytes(data=audio_bytes, mime_type='audio/wav')

            client = genai.Client(api_key=os.getenv("TOKEN"))

            response = client.models.generate_content(
                model='gemini-2.0-flash',
                contents=[
                    'Transcreva exatamente o que foi falado neste áudio. Não inclua interpretações, descrições ou explicações. Apenas o texto falado. sem quebra de linha.',
                    audio_part
                ]
            )

            return jsonify({"text": (response.text).replace("\n", "")}), 200
        else:
            return Response(status=400)
    except:
        return Response(status=500)

if __name__ == "__main__":
    app.run(debug=False, port=5000)