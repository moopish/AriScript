# Puella Script
A script parser and evaluator for a scripting language that I have thought of. This is for educational purposes.

### Syntax

<p>In this section, unless stated otherwise, {words} represents an idea of what goes in that location. The '{' and '}'
are not used in the context shown. i.e. <tt>${function name}:;</tt> means a function name goes where 
<tt>{function name}</tt> is.</p>

#### Basics

##### IDs with Type

<p>This is refering to when you need an identifier that has an associated type with it. These take the form of:
<br><pre>   <b><tt>{id}|{type}</tt></b></pre></p>

#### Types

<p>Types are represented with the '<tt>~</tt>' character.</p>

##### Defining

<p>Defining types will take the basic form of: <br><pre>   <b><tt>~{type name}:{attributes};</tt></b></pre></p>
<p>The type name must be alpha-characters only. The attributes follow the 'IDs with Type' concept above.</p>

##### New Instances

<p>Similar to defining types other than the separating character is different and the attributes must be values 
of the type specified for that attribute in the definition:
<br><pre>   <b><tt>~{type name}={attributes};</tt></b></pre></p>

#### Namespaces

<p>Namespaces are represented with the '<tt>#</tt>' character. All namespaces are stored with in the 
main namespace '<tt>ari</tt>'.</p>

##### Defining

<p>To define a namespace you simply do the following:
<br><pre>   <b><tt>#{namespace name}:{contents};</tt></b></pre></p></p>
<p>Names must be alpha-characters and the contents is simply the code that is in that namespace, 
can be more namespaces types, functions, anything.</p>

##### Getting from a Namespace

<p>To get from a specific namespace you use the '<tt>.</tt>' character. You do not need to include the main namespace
as it is the only namespace at that level. So to retrieve you may have code that has some parts that look like this:
<br><pre>   util.math.trig</pre></p>
