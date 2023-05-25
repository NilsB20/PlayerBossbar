#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;
in float depthLevel; // added: get depthLevel from vsh

out vec4 fragColor;

void main() {
    vec4 texColor = texture(Sampler0, texCoord0); // modified: separated texColor
    vec4 color = texColor * vertexColor * ColorModulator; // see above
    if (color.a < 0.1) {
        discard;
    }
    if(texColor.a == 160.0/255.0) { // added: check for aplha channel
        if(depthLevel == 0.00) discard;
    }
     
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}