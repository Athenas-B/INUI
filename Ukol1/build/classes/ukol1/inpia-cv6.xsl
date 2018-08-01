<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml" encoding="utf-8"/>

    <xsl:template match="/">
        <xsl:for-each select="/citaty/citat">
            <xsl:element name="{concat(name(), position())}">
                <xsl:call-template name="mujcitat">
                    <xsl:with-param name="citat" select="."/>
                </xsl:call-template>
            </xsl:element>
            <xsl:variable name="id" select="generate-id(./text)"/>
            <xsl:attribute name="id">
                <xsl:value-of select="$id"/>   
                   
            </xsl:attribute>
                
            <xsl:apply-templates select="text">
                <xsl:with-param name="id" select="'12'"/>
            </xsl:apply-templates>
                
            <xsl:attribute name="id">
                <xsl:value-of select="generate-id(.)">   
                </xsl:value-of>
            </xsl:attribute>
            <xsl:choose>
                <xsl:when test="position()&lt;2">
                    mensi nez 2
                </xsl:when>
                <xsl:when test="position()&gt;10">
                    vetsi nez 10
                </xsl:when>
                <xsl:otherwise>
                    mezi dva a deset
                </xsl:otherwise>
            </xsl:choose>
            <xsl:if test="name(..)='citaty' and string-length(./autor)&gt;16">      
                <xsl:comment>
                    <xsl:value-of select="autor">
                    </xsl:value-of>
                </xsl:comment>
            </xsl:if>
            <xsl:value-of select="text">
            </xsl:value-of>
            <xsl:processing-instruction name="php">
                php code
            </xsl:processing-instruction>
                
        </xsl:element>
        </xsl:for-each>
    
    </xsl:template>
    <xsl:template match="text">
        <xsl:param name="id" select="10"/>
        <text_node>
            <xsl:value-of select="$id" />
        </text_node>
    </xsl:template>
    <xsl:template name="mujcitat">
        <xsl:param name="cit" select=""/>
        <text_node>
            <xsl:value-of select="$cit" />
        </text_node>
    </xsl:template>
</xsl:stylesheet>
