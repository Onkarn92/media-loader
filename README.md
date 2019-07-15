# MediaLoader

[![Platform](https://img.shields.io/badge/platform-android-yellow.svg)]()
[![Programming Language](https://img.shields.io/badge/language-kotlin-orange.svg)]()
[![Scrutinizer Build](https://api.travis-ci.org/Onkarn92/media-loader.svg)]()
[![license](https://img.shields.io/github/license/mashape/apistatus.svg?maxAge=2592000)](/LICENSE.md)

Robust, reliable and concise media loading (i.e. Image, JSON, PDf, etc.) library for Android focused on smooth scrolling and supports in-built configurable memory cache.

## Table of content
- [Screens](#screens)
- [Usage](#usage)
- [Contributor](#contributor)
- [License](#license)

## Usage

For a working implementation of this project see the sample/ folder.

#### Single Line
<pre>MediaLoader.Builder<View>(context).load(url).into(imageView).create().download()</pre>

#### Download with pre-builded MediaLoader object
<pre>
val mediaLoader = MediaLoader.Builder<View>(context).load(url).into(imageView).create()
mediaLoader.download()
</pre>
  
#### Cancel the request
<pre>
val mediaLoader = MediaLoader.Builder<View>(context).load(url).into(imageView).create()
mediaLoader.cancel()
</pre>

## Contributor

* Onkar nene - omkarn.92@gmail.com

## License

```
MIT License

Copyright (c) 2019 Onkar Nene

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
