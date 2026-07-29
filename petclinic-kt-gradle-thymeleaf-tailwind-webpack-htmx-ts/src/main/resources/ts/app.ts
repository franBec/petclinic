import htmx from 'htmx.org';
import flatpickr from 'flatpickr';
import 'css/app.css';


/**
 * Register an event at the document for the specified selector,
 * so events are still catched after DOM changes.
 */
function handleEvent(eventType: string, selector: string, handler: (this: Element, event: Event) => any) {
  document.addEventListener(eventType, function(event) {
    const eventTarget = event.target! as Element;
    if (eventTarget.matches(selector + ', ' + selector + ' *')) {
      handler.apply(eventTarget.closest(selector)!, [event]);
    }
  });
}

handleEvent('change', '.js-selectlinks', function() {
  const selectElement = this as HTMLSelectElement;
  htmx.ajax('get', selectElement.value, document.body);
  history.pushState({ htmx: true }, '', selectElement.value);
});

handleEvent('click', 'body', function(event: Event) {
  // close any open dropdown
  const $clickedDropdown = (event.target! as Element).closest('.js-dropdown');
  const $dropdowns = document.querySelectorAll('.js-dropdown');
  $dropdowns.forEach(($dropdown) => {
    if ($clickedDropdown !== $dropdown && $dropdown.getAttribute('data-dropdown-keepopen') !== 'true') {
      $dropdown.ariaExpanded = 'false';
      $dropdown.nextElementSibling!.classList.add('hidden');
    }
  });
  // toggle selected if applicable
  if ($clickedDropdown) {
    $clickedDropdown.ariaExpanded = '' + ($clickedDropdown.ariaExpanded !== 'true');
    $clickedDropdown.nextElementSibling!.classList.toggle('hidden');
    event.preventDefault();
  }
});

function initDatepicker() {
  document.querySelectorAll('.js-datepicker, .js-timepicker, .js-datetimepicker').forEach(($item) => {
    const flatpickrConfig: any = {
      allowInput: true,
      time_24hr: true,
      enableSeconds: true
    };
    if ($item.classList.contains('js-datepicker')) {
      flatpickrConfig.dateFormat = 'Y-m-d';
    } else if ($item.classList.contains('js-timepicker')) {
      flatpickrConfig.enableTime = true;
      flatpickrConfig.noCalendar = true;
      flatpickrConfig.dateFormat = 'H:i:S';
    } else { // datetimepicker
      flatpickrConfig.enableTime = true;
      flatpickrConfig.altInput = true;
      flatpickrConfig.altFormat = 'Y-m-d H:i:S';
      flatpickrConfig.dateFormat = 'Y-m-dTH:i:S';
      // workaround label issue
      flatpickrConfig.onReady = function() {
        const id = this.input.id;
        this.input.id = null;
        this.altInput.id = id;
      };
    }
    flatpickr($item, flatpickrConfig);
  });
}
document.addEventListener('htmx:afterSwap', initDatepicker);
initDatepicker();
