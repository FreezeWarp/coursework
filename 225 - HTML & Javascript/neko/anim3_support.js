/*--------------------------------------------------------------
ADD: mouseTracker( function(x,y,button) )
	stores mouse in X, Y, B properties or calls given handler function instead

2do: IE6+ fixes positioning, not memory leak (you shouldn't run this more than once anyhow)
2do; getting button to work is a problem still buttons
bug: detecting right click button. quickly.  FF likes e.buttons; NN uses e.which but buggy.

example use:
	function my_function( x, y ){...code}
	id= mouseTracker( my_function );

example 2 use:
	id= mouseTracker();
	use window.X, window.Y

example stop tracker:
	window.removeEventListener( my_function );
*/
function mouseTracker( o ){
	if(window.addEventListener){
		var button=0;
		window.addEventListener('mousemove', (typeof o == 'function')? 
			function(e){
				o(e.pageX, e.pageY, button );
			}
			:
			function(e){
				window.X=	e.pageX;
				window.Y=	e.pageY;
				window.B=	button;
			}
		,true);
		window.addEventListener('mousedown', function(e){button=	e.buttons;}	,true);
		window.addEventListener('mouseup', function(e){button=	0;}	,true);
		
	}else if(document.attachEvent){//IE<9
		document.attachEvent('onmousemove', (typeof o == 'function')? 
			function(e){
				o( e.clientX	+	document.documentElement.scrollLeft
				  ,e.clientY	+	document.documentElement.scrollTop
				  ,((e.button & 1)? 1:	((e.button & 2)? 2:	((e.button & 4)? 2: 0)))
				  );
			}
			:
			function(e){
				window.X=	e.clientX	+	document.documentElement.scrollLeft;
				window.Y=	e.clientY	+	document.documentElement.scrollTop;
				window.B=	(e.button & 1)? 1:	((e.button & 2)? 2:	((e.button & 4)? 2: 0));
			}
		);
		
	}//killed off DOM0 support
}







/*--------------------------------------------------------------
keyTracker
	handles keyboard input for programs needing the ability to poll rather than event driven input
		Interactive animations need to poll input at specific TIMES because they run in real time
		not on demand like an event driven model requires.
	easiest is to use keyup & keypress with keypress always repeating when held down

2do: add new DOM 3 support
2do: native datatype array
2do: capture support, default.
2do: test modifier as key event when used with held key - do modifiers always fire too?

8	backspace
9	tab
13	return
16	shift
17	ctrl
18	alt
19	pause
20	capslock
27	esc
32	space
33	pageup
34	pagedown
35	end
36	home
37	left
38	up
39	right
40	down
45	insert
46	del
112	f1
113	f2
114	f3
115	f4
116	f5
117	f6
118	f7
119	f8
120	f9
121	f10
122	f11
123	f12
224	meta command
*/
keyTracker=function(o){//( [HTMLElement] )
	"use strict";
	this.e=	o ? o : window.addEventListener ? window : document;//IE keys on document
	this.key=	new Array(255);
	this.upHE=	{key:this.key, handleEvent:this.onKeyUp};
	this.add(this.e);
};
keyTracker.prototype={
	add:			function(e){"use strict";
		if( e.addEventListener ){
			e.addEventListener( 'keydown', this, false);
			e.addEventListener( 'keyup', this.upHE, false);
		}else if(e.attachEvent){//IE<9
			var me=		this;
			this.IE=	function(e){keyTracker.prototype.handleIE.call(me,e);};
			e.attachEvent( 'onkeydown', this.IE );
			e.attachEvent( 'onkeyup', this.IE );
		}
	}
	,remove: 		function(){"use strict";
		if( this.e.removeEventListener ){
			this.e.removeEventListener( 'keydown', this, false);
			this.e.removeEventListener( 'keyup', this.upHE, false);
		}else if(this.e.attachEvent){//IE<9
			this.e.detachEvent( 'onkeydown', this.IE);
			this.e.detachEvent( 'onkeyup', this.IE);
		}
	}
	,reset:			function(){"use strict";
		this.key.length=		0;//kill without malloc
	}
	,handleEvent: function(e){"use strict";//onKeyDown
		this.key[e.keyCode]=	true;
	}
	,onKeyUp:	function(e){"use strict";
		this.key[e.keyCode]=	false;
	}
	,handleIE:	function(e){"use strict";
		this.key[e.keyCode]=	e.type != 'keyup';
	}
};



//--------------------------------------------------------------
//FIX: browser window size
//for IE 6,7?
if(window['innerWidth']==undefined){var x=function(){window.innerWidth=document.documentElement.clientWidth;window.innerHeight=document.documentElement.clientHeight;};window.attachEvent('onresize',x);x();}



//--------------------------------------------------------------
//--------------------------------------------------------------
//--------------------------------------------------------------
//for students
mouseTracker( ); //setup mouse Tracker


//blank defaults to redefine - needed for feeLong since it lacks them.
function up(){}
function down(){}
function upright(){right();}
function upleft(){left();}
function downright(){right();}
function downleft(){left();}


//setup keyboard tracker
var poll=	new keyTracker();

//run in the main animation loop; polls keyboard for key presses
var lastDirection;
function handleKeyPresses(){
	if( poll.key[37] ){
		if( poll.key[38]){
			if(lastDirection !== upleft){
				lastDirection=	upleft;
				upleft();
			}
		}else if( poll.key[40]){
			if(lastDirection !== downleft){
				lastDirection=	downleft;
				downleft();
			}
		}else if( lastDirection !== left ){
			lastDirection=	left;
			left();
		}
		
	}else if( poll.key[39] ){
		if( poll.key[38] ){
			if(lastDirection !== upright){
				lastDirection=	upright;
				upright();
			}
		}else if( poll.key[40] ){
			if(lastDirection !== downright){
				lastDirection=	downright;
				downright();
			}
		}else if( lastDirection !== right ){
			lastDirection=	right;
			right();
		}
	
	}else if( poll.key[38] ){
		if(lastDirection !== up){
			lastDirection=	up;
			up();
		}
	}else if( poll.key[40] ){
		if(lastDirection !== down){
			lastDirection=	down;
			down();
		}
	}else if( lastDirection !== idle ){
		lastDirection=	idle;
		idle();
	}
}


