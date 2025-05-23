(function($) {
	"use strict"

	// Mobile Nav toggle
	$('.menu-toggle > a').on('click', function (e) {
		e.preventDefault();
		$('#responsive-nav').toggleClass('active');
	})

	// Fix cart dropdown from closing
	$('.cart-dropdown').on('click', function (e) {
		e.stopPropagation();
	});

	/////////////////////////////////////////

	// Products Slick
	$('.products-slick').each(function() {
		var $this = $(this),
				$nav = $this.attr('data-nav');

		$this.slick({
			slidesToShow: 4,
			slidesToScroll: 1,
			autoplay: true,
			infinite: true,
			speed: 300,
			dots: false,
			arrows: true,
			appendArrows: $nav ? $nav : false,
			responsive: [{
	        breakpoint: 991,
	        settings: {
	          slidesToShow: 2,
	          slidesToScroll: 1,
	        }
	      },
	      {
	        breakpoint: 480,
	        settings: {
	          slidesToShow: 1,
	          slidesToScroll: 1,
	        }
	      },
	    ]
		});
	});

	// Products Widget Slick
	$('.products-widget-slick').each(function() {
		var $this = $(this),
				$nav = $this.attr('data-nav');

		$this.slick({
			infinite: true,
			autoplay: true,
			speed: 300,
			dots: false,
			arrows: true,
			appendArrows: $nav ? $nav : false,
		});
	});

	/////////////////////////////////////////

	// Product Main img Slick
	$('#product-main-img').slick({
    infinite: true,
    speed: 300,
    dots: false,
    arrows: true,
    fade: true,
    asNavFor: '#product-imgs',
  });

	// Product imgs Slick
  $('#product-imgs').slick({
    slidesToShow: 3,
    slidesToScroll: 1,
    arrows: true,
    centerMode: true,
    focusOnSelect: true,
		centerPadding: 0,
		vertical: true,
    asNavFor: '#product-main-img',
		responsive: [{
        breakpoint: 991,
        settings: {
					vertical: false,
					arrows: false,
					dots: true,
        }
      },
    ]
  });

	// Product img zoom
	var zoomMainProduct = document.getElementById('product-main-img');
	if (zoomMainProduct) {
		$('#product-main-img .product-preview').zoom();
	}

	/////////////////////////////////////////

	// Input number
	$('.input-number').each(function() {
		var $this = $(this),
		$input = $this.find('input[type="number"]'),
		up = $this.find('.qty-up'),
		down = $this.find('.qty-down');

		down.on('click', function () {
			var value = parseInt($input.val()) - 1;
			value = value < 1 ? 1 : value;
			$input.val(value);
			$input.change();
			updatePriceSlider($this , value)
		})

		up.on('click', function () {
			var value = parseInt($input.val()) + 1;
			$input.val(value);
			$input.change();
			updatePriceSlider($this , value)
		})
	});

	var priceInputMax = document.getElementById('price-max'),
			priceInputMin = document.getElementById('price-min');

	priceInputMax.addEventListener('change', function(){
		updatePriceSlider($(this).parent() , this.value)
	});

	priceInputMin.addEventListener('change', function(){
		updatePriceSlider($(this).parent() , this.value)
	});

	function updatePriceSlider(elem , value) {
		if ( elem.hasClass('price-min') ) {
			console.log('min')
			priceSlider.noUiSlider.set([value, null]);
		} else if ( elem.hasClass('price-max')) {
			console.log('max')
			priceSlider.noUiSlider.set([null, value]);
		}
	}

	// Price Slider
	var priceSlider = document.getElementById('price-slider');
	if (priceSlider) {
		noUiSlider.create(priceSlider, {
			start: [1, 999],
			connect: true,
			step: 1,
			range: {
				'min': 1,
				'max': 999
			}
		});

		priceSlider.noUiSlider.on('update', function( values, handle ) {
			var value = values[handle];
			handle ? priceInputMax.value = value : priceInputMin.value = value
		});
	}

})(jQuery);
/**
 * Search Form Handling
 *
 * Bước 5.3: Kiểm tra dữ liệu đầu vào
 * Bước 5.5: Cải thiện hiển thị kết quả tìm kiếm
 */
$(document).ready(function() {
	// Bước 5.3: Kiểm tra tính hợp lệ của form tìm kiếm
	$('.header-search form').on('submit', function(e) {
		var searchInput = $(this).find('input[name="search"]');
		var searchValue = searchInput.val().trim();

		// Luồng thay thế: Từ khóa không hợp lệ - kiểm tra từ khóa rỗng
		if (searchValue === '') {
			// Ngăn việc submit form
			e.preventDefault();

			// Hiển thị thông báo
			alert('Vui lòng nhập từ khóa.');

			// Focus vào ô input
			searchInput.focus();
			return;
		}

		// Kiểm tra từ khóa chứa ký tự đặc biệt
		var specialChars = /[!@#$%^&*(),.?":{}|<>]/;
		if (specialChars.test(searchValue)) {
			// Ngăn việc submit form
			e.preventDefault();

			// Hiển thị thông báo
			alert('Từ khóa không hợp lệ. Vui lòng không sử dụng ký tự đặc biệt.');

			// Focus vào ô input
			searchInput.focus();
		}
	});

	// Bước 5.5: Highlight từ khóa tìm kiếm trong kết quả hiển thị
	if (window.location.pathname.indexOf('search') > -1) {
		// Lấy từ khóa tìm kiếm từ URL
		var searchQuery = new URLSearchParams(window.location.search).get('search');

		if (searchQuery) {
			// Tách từ khóa thành các từ
			var searchTerms = searchQuery.split(/\s+/);

			// Highlight mỗi từ trong tên sản phẩm
			$('.product-name a').each(function() {
				var text = $(this).text();

				searchTerms.forEach(function(term) {
					if (term.length > 2) { // Chỉ highlight từ có 3+ ký tự
						var regex = new RegExp('(' + term + ')', 'gi');
						text = text.replace(regex, '<span class="highlight">$1</span>');
					}
				});

				$(this).html(text);
			});

			// Thêm style highlight
			$('<style>.highlight { background-color: #ffff99; }</style>').appendTo('head');
		}
	}
});